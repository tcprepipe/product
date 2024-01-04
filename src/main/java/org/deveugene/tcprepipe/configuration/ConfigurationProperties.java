package org.deveugene.tcprepipe.configuration;

public interface ConfigurationProperties {
    String tcprepipePort();
    String workingMode();
    String clientsProxy();
    String countWorkingThreads();
    String maxConnections();
    String trafficCapacity();
    String encryptionActive();
    String encryptionAlgorithm();
}
