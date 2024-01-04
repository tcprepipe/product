package org.deveugene.tcprepipe.configuration;

import org.deveugene.tcprepipe.core.PerformanceMode;

public record PerformanceModeImpl(short countWorkThreads, int maxConnections, int trafficCapacity)
        implements PerformanceMode {
    @Override
    public short getCountWorkThreads() {
        return countWorkThreads;
    }

    @Override
    public int getMaxConnections() {
        return maxConnections;
    }

    @Override
    public int getTrafficCapacity() {
        return trafficCapacity;
    }
}
