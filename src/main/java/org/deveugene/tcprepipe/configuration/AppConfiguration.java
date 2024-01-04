package org.deveugene.tcprepipe.configuration;

import lombok.Getter;
import org.deveugene.tcprepipe.core.EncryptionMode;
import org.deveugene.tcprepipe.core.PerformanceMode;
import org.deveugene.tcprepipe.core.WorkingMode;
import org.deveugene.tcprepipe.core.network.Port;

@Getter
public abstract class AppConfiguration {
    private int tcprepipePort;
    private WorkingMode mode;
    private Iterable<Port> clientsProxy;
    private PerformanceMode performanceMode;
    private EncryptionMode encryptionMode;

    abstract public void configure();

    protected void configure(int port, WorkingMode mode, Iterable<Port> clients) {
        this.tcprepipePort = port;
        this.mode = mode;
        this.clientsProxy = clients;
    }

    protected void configurePerformance(PerformanceMode performance) {
        this.performanceMode = performance;
    }

    protected void configureEncryption(EncryptionMode encryption) {
        this.encryptionMode = encryption;
    }

    public static class DefaultValueNotFoundException extends RuntimeException {}
}
