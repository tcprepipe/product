package org.deveugene.tcprepipe.configuration;

import org.deveugene.tcprepipe.configuration.validation.EncryptionValidation;
import org.deveugene.tcprepipe.configuration.validation.PerformanceValidation;
import org.deveugene.tcprepipe.core.EncryptionMode;
import org.deveugene.tcprepipe.core.PerformanceMode;

public final class Configurator {
    private Configurator() {
    }

    public static PerformanceMode configurePerformance(String workingThreads, String maxConnections,
                                                       String trafficCapacity) {
        new PerformanceValidation(workingThreads, maxConnections, trafficCapacity).validateThrow();
        return new PerformanceModeImpl(Short.parseShort(workingThreads), Integer.parseInt(maxConnections), Integer.parseInt(trafficCapacity));
    }

    public static EncryptionMode configureEncryption(String active, String algorithm) {
        new EncryptionValidation(active, algorithm).validateThrow();
        return new EncryptionModeImpl(Boolean.parseBoolean(active), algorithm);
    }
}
