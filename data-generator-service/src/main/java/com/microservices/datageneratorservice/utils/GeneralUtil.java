package com.microservices.datageneratorservice.utils;

import com.microservices.datageneratorservice.model.DeviceValueBehaviourImitationRule;
import com.microservices.datageneratorservice.model.DeviceValueProperty;
import com.microservices.datageneratorservice.model.enums.DeviceValueChangeType;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class GeneralUtil {

    private static SecureRandom random = new SecureRandom();

    public static LocalDateTime getNextUpdateTime(long currentStateMs, long currentStateDeltaMs) {
        int randomDeltaMs = random.nextInt((int) currentStateDeltaMs);
        boolean deltaMsPlusOrMinus = Math.random() < 0.5;

        long nextUpdateMs = (deltaMsPlusOrMinus) ? currentStateMs + randomDeltaMs : currentStateMs - randomDeltaMs;
        return LocalDateTime.now().plus(nextUpdateMs, ChronoUnit.MILLIS);
    }

    public static double getNextValue(double currentValue, DeviceValueBehaviourImitationRule rule, DeviceValueProperty property) {
        DeviceValueChangeType valueChangeType = property.getChangeType();
        if (property.getNextChangeTypeTime().isBefore(LocalDateTime.now())) {
            valueChangeType = (valueChangeType == rule.getChangeTypeOne()) ? rule.getChangeTypeTwo() : rule.getChangeTypeOne();
            property.setChangeType(valueChangeType);
            property.setNextChangeTypeTime(LocalDateTime.now().plus(rule.getTypeChangeDurationMs(), ChronoUnit.MILLIS));
        }
        double changeStep;
        if (rule.getChangeStepDelta() != null) {
            changeStep = (Math.random() < 0.5)
                ? rule.getChangeStep() + random.nextInt(rule.getChangeStepDelta())
                : rule.getChangeStep() - random.nextInt(rule.getChangeStepDelta());
        } else {
            changeStep = rule.getChangeStep();
        }

        double newValue;
        switch (valueChangeType) {
            case IDLE:
            case NONE:
                newValue = currentValue;
                break;
            case INCREASE:
                newValue = currentValue + changeStep;
                break;
            case DECREASE:
                newValue = currentValue - changeStep;
                break;
            default:
                throw new RuntimeException("CHANGE types should be filled for column " + rule.getValueName());
        }
        if (rule.getMin() != null && newValue < rule.getMin()) {
            newValue = rule.getMin();
        } else if (rule.getMax() != null && newValue > rule.getMax()) {
            newValue = rule.getMax();
        }

        return newValue;
    }


}
