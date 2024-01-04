package org.deveugene.tcprepipe.core;

import org.deveugene.tcprepipe.configuration.AppConfiguration;
import org.deveugene.tcprepipe.configuration.ConfigurationLoader;
import org.deveugene.tcprepipe.configuration.Configurator;
import org.deveugene.tcprepipe.configuration.parse.PortValueParser;
import org.deveugene.tcprepipe.configuration.validation.MainValidation;

public class TcpRePipeSettings extends AppConfiguration {
    public static final String CONFIG = "TCPREPIPE_CONFIG";
    private volatile static TcpRePipeSettings instance;

    private final ConfigurationLoader loader;

    private TcpRePipeSettings() {
        this.loader = new ConfigurationLoader(System.getenv(TcpRePipeSettings.CONFIG));
    }

    public synchronized static TcpRePipeSettings getInstance() {
        if (TcpRePipeSettings.instance == null) {
            TcpRePipeSettings.instance = new TcpRePipeSettings();
        }

        return TcpRePipeSettings.instance;
    }

    @Override
    public void configure() {
        new MainValidation(this.loader.tcprepipePort(), this.loader.workingMode()).validateThrow();
        PortValueParser parser = new PortValueParser(this.loader.clientsProxy());
        super.configure(Integer.parseInt(this.loader.tcprepipePort()), WorkingMode.valueOf(this.loader.workingMode()), parser.getPorts());

        super.configurePerformance(Configurator.configurePerformance(this.loader.countWorkingThreads(),
                this.loader.maxConnections(), this.loader.trafficCapacity()));

        super.configureEncryption(Configurator.configureEncryption(this.loader.encryptionActive(),
                this.loader.encryptionAlgorithm()));
    }
}
