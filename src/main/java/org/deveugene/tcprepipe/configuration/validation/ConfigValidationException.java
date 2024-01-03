package org.deveugene.tcprepipe.configuration.validation;

public class ConfigValidationException extends RuntimeException {
    private final Validation validation;

    public ConfigValidationException(Validation validation) {
        super("The %s class has not been validated: %s".formatted(validation.getClass().getName(), validation.toString()));
        this.validation = validation;
    }
}
