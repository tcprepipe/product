package org.deveugene.tcprepipe.configuration.validation;

import lombok.Getter;

@Getter
public abstract class Validation {
    public static boolean isInteger(String value) {
        return value.matches("\\d+");
    }

    abstract boolean validate();

    public void validateThrow() {
        if (!validate()) {
            throw new ConfigValidationException(this);
        }
    }
}
