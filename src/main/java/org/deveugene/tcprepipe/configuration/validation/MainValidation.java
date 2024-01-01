package org.deveugene.tcprepipe.configuration.validation;

import org.deveugene.tcprepipe.core.WorkingMode;

public class MainValidation extends Validation {
    public static final int MAX_PORTS = 65535;
    public static final int MIN_PORT_VALUE = 0;

    private final String port, workingMode;

    public MainValidation(String port, String workingMode) {
        this.port = port;
        this.workingMode = workingMode;
    }

    public boolean validatePort() {
        if (!this.port.matches("\\d+")) return false;
        int port = Integer.parseInt(this.port);
        return port <= MAX_PORTS && port > MIN_PORT_VALUE;
    }

    public boolean validateMode() {
        if (!this.workingMode.matches("[A-Z]+")) return false;
        for (WorkingMode mode : WorkingMode.values()) {
            if (mode.name().equals(this.workingMode)) {
                return true;
            }
        }

        return false;
    }

    @Override
    boolean validate() {
        return validatePort() && validateMode();
    }
}
