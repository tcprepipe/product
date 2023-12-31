package org.deveugene.tcprepipe.configuration;

import org.deveugene.tcprepipe.core.EncryptionMode;
import org.deveugene.tcprepipe.core.PerformanceMode;
import org.deveugene.tcprepipe.core.WorkingMode;
import org.deveugene.tcprepipe.core.network.Port;

public interface Configuration {
    int getTcprepipePort();
    PerformanceMode getPerformanceMode();
    EncryptionMode getEncryptionMode();
    Iterable<Port> getClientsProxy();
    WorkingMode getMode();
}
