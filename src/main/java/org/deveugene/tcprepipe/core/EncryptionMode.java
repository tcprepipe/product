package org.deveugene.tcprepipe.core;

public interface EncryptionMode {
    boolean isActive();
    String getAlgorithm();
}
