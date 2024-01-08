package org.deveugene.tcprepipe.configuration;

import org.deveugene.tcprepipe.configuration.loader.Loader;

import java.util.Properties;

public class ConfigurationRegistry implements ConfigurationProperties {
    private final Loader loader;
    private Properties properties;

    public ConfigurationRegistry(Loader loader) {
        this.loader = loader;
    }

    @Override
    public String tcprepipePort() {
        return loadConfigProperty(Key.PORT.key, Key.PORT.defOrNull());
    }

    @Override
    public String workingMode() {
        return loadConfigProperty(Key.WORKING_MODE.key, Key.WORKING_MODE.defOrNull());
    }

    @Override
    public String clientsProxy() {
        return loadConfigProperty(Key.PORTS_PROXY_CLIENTS.key, Key.PORTS_PROXY_CLIENTS.defOrNull());
    }

    @Override
    public String countWorkingThreads() {
        return loadConfigProperty(Key.WORKING_THREADS_COUNT.key, Key.WORKING_THREADS_COUNT.defOrNull());
    }

    @Override
    public String maxConnections() {
        return loadConfigProperty(Key.MAX_CONNECTIONS.key, Key.MAX_CONNECTIONS.defOrNull());
    }

    @Override
    public String trafficCapacity() {
        return loadConfigProperty(Key.TRAFFIC_CAPACITY.key, Key.TRAFFIC_CAPACITY.defOrNull());
    }

    @Override
    public String encryptionActive() {
        return loadConfigProperty(Key.ENCRYPTION_USE.key, Key.ENCRYPTION_USE.defOrNull());
    }

    @Override
    public String encryptionAlgorithm() {
        return loadConfigProperty(Key.ENCRYPTION_ALGORITHM.key, Key.PORTS_PROXY_CLIENTS.defOrNull());
    }

    private String loadConfigProperty(String key, String def) {
        if (this.properties == null) {
            loadResources();
        }

        return this.properties.getProperty(key, def);
    }

    private void loadResources() {
        this.loader.loadResources(this.properties = new Properties());
    }
}
