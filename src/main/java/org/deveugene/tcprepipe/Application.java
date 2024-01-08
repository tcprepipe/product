package org.deveugene.tcprepipe;

import lombok.Getter;
import org.deveugene.tcprepipe.configuration.AppConfiguration;
import org.deveugene.tcprepipe.configuration.ConfigurationRegistry;
import org.deveugene.tcprepipe.core.TcpRePipeSettings;

@Getter
public abstract class Application {
    @Getter
    private volatile static Application instance;

    private String[] setupArgs;
    private ConfigurationRegistry configurationRegistry;
    private AppConfiguration configuration;

    public Application() {
        Application.instance = this;
    }

    public final void start(String[] args) {
        init(args);
    }

    private void init(String[] args) {
        this.setupArgs = args;

        this.configurationRegistry = new ConfigurationRegistry(null);

        this.configuration = new TcpRePipeSettings(this.configurationRegistry);
        configuration.configure();
    }
}
