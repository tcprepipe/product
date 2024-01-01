package org.deveugene.tcprepipe.configuration.validation;

import org.deveugene.tcprepipe.core.encryption.Algorithms;

public class EncryptionValidation extends Validation {
    private final String active, algorithm;

    public EncryptionValidation(String active, String algorithm) {
        this.active = active;
        this.algorithm = algorithm;
    }

    public boolean validateActive() {
        return "true".equals(this.active) || "false".equals(this.active);
    }

    public boolean validateAlgorithm() {
        for (Algorithms algorithm : Algorithms.values()) {
            if (algorithm.name().equals(this.algorithm)) {
                return true;
            }
        }

        return false;
    }

    @Override
    boolean validate() {
        return validateActive() && validateAlgorithm();
    }
}
