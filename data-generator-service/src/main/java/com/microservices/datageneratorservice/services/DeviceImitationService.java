package com.microservices.datageneratorservice.services;

import com.microservices.datageneratorservice.model.Device;
import com.microservices.datageneratorservice.model.DevicePropertyImitationRule;
import com.microservices.datageneratorservice.model.DevicePropertyValueDetails;
import com.microservices.datageneratorservice.model.enums.DeviceType;
import com.microservices.datageneratorservice.model.enums.DeviceValueChangeType;
import com.microservices.datageneratorservice.repository.DevicePropertyImitationRuleRepository;
import com.microservices.datageneratorservice.repository.DevicePropertyValueDetailsRepository;
import com.microservices.datageneratorservice.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceImitationService {

    // beans
    private final DeviceRepository deviceRepository;
    private final DevicePropertyImitationRuleRepository propertyImitationRuleRepo;
    private final DevicePropertyValueDetailsRepository devicePropertyValueDetailsRepo;
    // values
    private final SecureRandom random = new SecureRandom();

    @Transactional
    public Device generateDeviceWithPopulatedValues(DeviceType deviceType) {
        final long deviceAmount = this.deviceRepository.count();
        // load random smart plug
        final long randomNum = ThreadLocalRandom.current().nextLong(1, deviceAmount + 1L) - 1;
        final String randomDeviceUUID = this.deviceRepository.findDeviceIDListByDeviceType(deviceType).get((int) randomNum);
        Device curDevice = this.deviceRepository.findById(randomDeviceUUID).orElseThrow(IllegalStateException::new);
        // generate device columns
        final Map<String, String> oldDeviceProperties = curDevice.getPropertyValueMap();
        final Map<String, String> newDeviceProperties = new TreeMap<>();
        List<String> deviceTypeProps = this.propertyImitationRuleRepo.findPropertyNamesOfDeviceType(deviceType);
        for (String curProperty : deviceTypeProps) {
            DevicePropertyImitationRule rule = this.propertyImitationRuleRepo.findFirstByDeviceTypeAndProperty(deviceType, curProperty);

            double newPropertyVal = this.generateNewPropertyValue(curProperty, curDevice, oldDeviceProperties, newDeviceProperties);

            curDevice.setGenerationStopped(rule.isStopGenIfMin() && newPropertyVal == 0);
        }
        curDevice.setPropertyValueMap(newDeviceProperties);
        curDevice.setUpdateDateTime(LocalDateTime.now());

        return this.deviceRepository.save(curDevice);
    }

    private LocalDateTime getNextUpdateTime(Long currentStateMs, Long currentStateDeltaMs) {
        long randomDeltaMs = (currentStateDeltaMs == null) ? 0 : this.random.nextInt(currentStateDeltaMs.intValue());
        boolean deltaMsPlusOrMinus = Math.random() < 0.5;

        long nextUpdateMs = (deltaMsPlusOrMinus) ? currentStateMs + randomDeltaMs : currentStateMs - randomDeltaMs;

        return LocalDateTime.now().plus(nextUpdateMs, ChronoUnit.MILLIS);
    }

    private double generateNewPropertyValue(final String property, final Device device, final Map<String, String> oldProperties, Map<String, String> newProperties) {
        final double oldPropertyValue = Double.parseDouble(oldProperties.get(property));
        double newValue = oldPropertyValue;
        if (newProperties.containsKey(property)) {
            return Double.parseDouble(newProperties.get(property));
        }
        DevicePropertyImitationRule rule = this.propertyImitationRuleRepo.findFirstByDeviceTypeAndProperty(device.getDeviceType(), property);
        // change value if dependent "trigger" property has been updated
        String triggeredByProp = rule.getTriggerByProp();
        if (triggeredByProp != null) {
            double triggeredByPropVal = this.generateNewPropertyValue(triggeredByProp, device, oldProperties, newProperties);
            double setValOnTriggeredByProp = rule.getSetValOnTriggerByProp();
            if (rule.getTriggerByPropVal() == triggeredByPropVal) {
                newProperties.put(property, String.valueOf(setValOnTriggeredByProp));
                return setValOnTriggeredByProp;
            }
        }
        DevicePropertyValueDetails propValDet = this.devicePropertyValueDetailsRepo.findByDeviceAndProperty(device, property)
                .orElse(new DevicePropertyValueDetails(device, property));
        if (propValDet.getNextUpdateTime().isAfter(LocalDateTime.now())) {
            newProperties.put(property, String.valueOf(oldPropertyValue));
            return oldPropertyValue;
        }
        // correlation coefficient
        String correlatedProperty = rule.getCorrelatedProperty();
        if (correlatedProperty != null) {
            double correlationCoef = rule.getCorrelationCoef();
            double correlatedPropertyOldValue = Double.parseDouble(oldProperties.get(correlatedProperty));
            double correlatedPropertyNewValue = this.generateNewPropertyValue(correlatedProperty, device, oldProperties, newProperties);
            double correlatedPropertyDelta = correlatedPropertyNewValue - correlatedPropertyOldValue;
            newValue = newValue + (correlatedPropertyDelta * correlationCoef);
        }
        DeviceValueChangeType newValueChangeType = (Math.random() < 0.5) ? rule.getChangeTypeOne() : rule.getChangeTypeTwo();
        if (propValDet.getNextChangeTypeTime().isAfter(LocalDateTime.now())) {
            newValueChangeType = propValDet.getChangeType();
        }
        propValDet.setChangeType(newValueChangeType);
        propValDet.setNextChangeTypeTime(LocalDateTime.now().plus(rule.getTypeChangeDurationMs(), ChronoUnit.MILLIS));
        propValDet.setNextUpdateTime(getNextUpdateTime(rule.getChangeDurationMs(), rule.getChangeDurationDeltaMs()));
        this.devicePropertyValueDetailsRepo.save(propValDet);

        double changeStep = rule.getChangeStep();;
        Integer changeStepDelta = rule.getChangeStepDelta();
        if (changeStepDelta != null) {
            changeStep = (Math.random() < 0.5) ? changeStep + random.nextInt(changeStepDelta) : changeStep - random.nextInt(changeStepDelta);
        }
        switch (newValueChangeType) {
            case IDLE:
                break;
            case INCREASE:
                newValue = newValue + changeStep;
                break;
            case DECREASE:
                newValue = newValue - changeStep;
                break;
            default:
                throw new RuntimeException("CHANGE types should be filled for column " + rule.getProperty() + " of device type " + rule.getDeviceType().name());
        }
        // if value is too low, increase it a little, so it doesn't stay at min
        if (rule.getMax() != null && rule.getMax() > 1 && newValue < (rule.getMax() / 3)) {
            newValue += random.nextInt((int) (rule.getMax() / 3));
        }
        // write result into the result map
        newValue = this.roundAndApplyBoundariesToTheNewValue(newValue, rule.getMin(), rule.getMax());
        newProperties.put(property, String.valueOf(newValue));

        return newValue;
    }

    private double roundAndApplyBoundariesToTheNewValue(Double newValue, final Double min, final Double max) {
        if (min != null && newValue < min) {
            newValue = min;
        } else if (max != null && newValue > max) {
            newValue =  max;
        }
        return BigDecimal.valueOf(newValue).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

}
