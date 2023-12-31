package org.deveugene.tcprepipe.core;

public interface PerformanceMode {
    short getCountWorkThreads();
    int getMaxConnections();
    int getTrafficCapacity();
}
