package org.deveugene.tcprepipe.configuration;

import org.deveugene.tcprepipe.core.EncryptionMode;

public record EncryptionModeImpl(boolean active, String algorithm) implements EncryptionMode {
    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String getAlgorithm() {
        return algorithm;
    }
}
