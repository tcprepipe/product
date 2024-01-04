package org.deveugene.tcprepipe.configuration;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigurationLoader implements ConfigurationProperties {
    public final String config;
    private Properties properties;

    public ConfigurationLoader(String config) {
        this.config = config;
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
        String settings = this.config;
        try {
            try {
                Path settingsPath = Paths.get(settings);
                if (settingsPath.isAbsolute() && Files.isRegularFile(settingsPath)) {
                    settings = Files.readString(settingsPath, StandardCharsets.UTF_8);
                }
            } catch (InvalidPathException ignored) {}

            StringReader reader = new StringReader(settings);
            this.properties = new Properties();
            this.properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("File not found.", e);
        }
    }
}
