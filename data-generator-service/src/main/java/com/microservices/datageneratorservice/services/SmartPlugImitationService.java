package com.microservices.datageneratorservice.services;

import com.microservices.datageneratorservice.model.DeviceValueBehaviourImitationRule;
import com.microservices.datageneratorservice.model.DeviceValueProperty;
import com.microservices.datageneratorservice.model.SmartPlug;
import com.microservices.datageneratorservice.model.enums.DeviceState;
import com.microservices.datageneratorservice.model.enums.DeviceType;
import com.microservices.datageneratorservice.model.enums.DeviceValueChangeType;
import com.microservices.datageneratorservice.repository.DeviceValueBehaviourImitationRuleRepository;
import com.microservices.datageneratorservice.repository.DeviceValuePropertyRepository;
import com.microservices.datageneratorservice.repository.SmartPlugRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.microservices.datageneratorservice.utils.GeneralUtil.getNextUpdateTime;
import static com.microservices.datageneratorservice.utils.GeneralUtil.getNextValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmartPlugImitationService {

    private final SmartPlugRepository repository;
    private final DeviceValueBehaviourImitationRuleRepository deviceBehaviourRepo;
    private final DeviceValuePropertyRepository valuePropertyRepository;

    private Integer deviceAmount;

    public SmartPlug generateDeviceWithPopulatedValues() {
        if (deviceAmount == null) {
            this.deviceAmount = this.repository.findAll().size();
        }
        // load random smart plug
        long randomRecId = ThreadLocalRandom.current().nextLong(1, this.deviceAmount + 1L);
        SmartPlug smartPlug = this.repository.findById(randomRecId)
                .orElseThrow(IllegalStateException::new);
        // load device behaviour rules
        List<DeviceValueBehaviourImitationRule> valueImitationRules = this.deviceBehaviourRepo.findAllByDeviceType(DeviceType.SMART_PLUG);
        if (valueImitationRules == null || valueImitationRules.isEmpty()) {
            throw new RuntimeException("Device value behavior imitation rules are not defined. Device type: " + DeviceType.SMART_PLUG.name);
        }
        // generate device columns
        smartPlug.getDevice().setDateTime(LocalDateTime.now());
        smartPlug.getDevice().setDeviceState(DeviceState.values()[(int) this.processDeviceColumnValue(smartPlug, "deviceState", smartPlug.getDevice().getDeviceState().ordinal(), valueImitationRules)]);
        // TODO: manage values in ON/OFF states based on imitation rules. See DeviceValueBehaviourImitationRule
        if (DeviceState.ON == smartPlug.getDevice().getDeviceState()) {
            smartPlug.getDevice().setBatteryLevel((int) this.processDeviceColumnValue(smartPlug, "batteryLevel", smartPlug.getDevice().getBatteryLevel(), valueImitationRules));
            smartPlug.getDevice().setWorkingTimeMin((long) this.processDeviceColumnValue(smartPlug, "workingTimeMin", smartPlug.getDevice().getWorkingTimeMin(), valueImitationRules));
            smartPlug.getDevice().setSignalStrength((int) this.processDeviceColumnValue(smartPlug, "signalStrength", smartPlug.getDevice().getSignalStrength(), valueImitationRules));
            smartPlug.setPower(this.processDeviceColumnValue(smartPlug, "power", smartPlug.getPower(), valueImitationRules));
            smartPlug.setLedLight((int) this.processDeviceColumnValue(smartPlug, "ledLight", smartPlug.getLedLight(), valueImitationRules));
            smartPlug.setChipTemperature(this.processDeviceColumnValue(smartPlug, "chipTemperature", smartPlug.getChipTemperature(), valueImitationRules));
        } else {
            // what happens if device is turned off
            smartPlug.getDevice().setWorkingTimeMin(0L);
            smartPlug.getDevice().setSignalStrength((int) this.processDeviceColumnValue(smartPlug, "signalStrength", smartPlug.getDevice().getSignalStrength(), valueImitationRules));
            smartPlug.setPower(0.0);
            smartPlug.setLedLight(0);
            smartPlug.setChipTemperature(10.0);
        }

        return this.repository.save(smartPlug);
    }

    private double processDeviceColumnValue(SmartPlug plug, String column, double currentValue, List<DeviceValueBehaviourImitationRule> valueImitationRules) {
        DeviceValueProperty property = this.valuePropertyRepository.findByDeviceAndValueName(plug.getDevice(), column);
        // init default column properties if not yet in DB
        if (property == null) {
            property = valuePropertyRepository.save(DeviceValueProperty.builder()
                    .changeType(DeviceValueChangeType.NONE)
                    .nextUpdateTime(LocalDateTime.MIN)
                    .nextChangeTypeTime(LocalDateTime.MIN)
                    .valueName(column)
                    .device(plug.getDevice())
                    .build());
        }
        if (property.getNextUpdateTime().isBefore(LocalDateTime.now())) {
            DeviceValueBehaviourImitationRule rule = valueImitationRules.stream()
                    .filter(x -> x.getValueName().equals(column))
                    .findFirst().orElseThrow(IllegalStateException::new);
            property.setNextUpdateTime(getNextUpdateTime(rule.getChangeDurationMs(), rule.getChangeDurationDeltaMs()));
            this.valuePropertyRepository.save(property);

            return getNextValue(currentValue, rule, property);
        } else {
            return currentValue;
        }
    }


}
