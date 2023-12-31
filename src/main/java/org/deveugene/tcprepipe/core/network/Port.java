package org.deveugene.tcprepipe.core.network;

public interface Port {
    int getValue();
    boolean isRange();
    int[] getValues();
}
