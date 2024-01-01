package org.deveugene.tcprepipe.configuration.validation;

import lombok.Getter;

@Getter
public class IncorrectPortException extends RuntimeException {
    private final String port;

    public IncorrectPortException(String port) {
        super("An incorrect port value was passed: '%s'.".formatted(port));
        this.port = port;
    }
}
