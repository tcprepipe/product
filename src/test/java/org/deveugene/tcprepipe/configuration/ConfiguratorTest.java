package org.deveugene.tcprepipe.configuration;

import org.deveugene.tcprepipe.core.EncryptionMode;
import org.deveugene.tcprepipe.core.PerformanceMode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfiguratorTest {
    @Test
    void configureShouldReturnCorrectPerformanceMode() {
        String workingThreads = "10";
        String maxConnections = "15";
        String trafficCapacity = "20";
        PerformanceMode performanceMode = Configurator.configurePerformance(workingThreads, maxConnections,
                trafficCapacity);
        assertEquals(Short.parseShort(workingThreads), performanceMode.getCountWorkThreads());
        assertEquals(Integer.parseInt(maxConnections), performanceMode.getMaxConnections());
        assertEquals(Integer.parseInt(trafficCapacity), performanceMode.getTrafficCapacity());
    }

    @Test
    void configureShouldReturnCorrectEncryptionMode() {
        String active = "true";
        String algorithm = "RSA";
        EncryptionMode encryptionMode = Configurator.configureEncryption(active, algorithm);
        assertEquals(Boolean.parseBoolean(active), encryptionMode.isActive());
        assertEquals(algorithm, encryptionMode.getAlgorithm());
    }
}
