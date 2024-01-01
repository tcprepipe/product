package org.deveugene.tcprepipe.core;

public enum WorkingMode {
    SERVER, CLIENT;

    public static WorkingMode of(String mode) {
        return valueOf(mode.toUpperCase());
    }
}
