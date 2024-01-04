package org.deveugene.tcprepipe.configuration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfigurationLoaderTest {
    private static String envWithPath, envWithStringAll, envWithString;

    private final String
        port = "35055",
        workingThreads = "5",
        maxConnections = "100",
        trafficCapacity = "167772160",
        encryptionUse = "false",
        encryptionAlgorithm = "RSA",
        clientsProxy = "1924,1925,1926",
        workingMode = "SERVER";

    @BeforeAll
    static void init() throws IOException {
        InputStream stream = ConfigurationLoaderTest.class
                .getClassLoader()
                .getResourceAsStream("tcprepipe.configuration.test");
        envWithStringAll = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        stream.close();

        envWithString = envWithStringAll.substring(0, envWithStringAll.indexOf("tcprepipe.performance.traffic.capacity"));

        Path envFile = Files.createTempFile("test", UUID.randomUUID().toString());
        envWithPath = envFile.toString();

        try (FileOutputStream fos = new FileOutputStream(envFile.toFile())) {
            fos.write(envWithStringAll.getBytes(StandardCharsets.UTF_8));
        }
    }

    @Test
    void testInit() {
        assertFalse(envWithPath.isBlank() || envWithPath.isEmpty() || envWithStringAll.isEmpty()
                || envWithStringAll.isBlank() || envWithString.isBlank() || envWithString.isEmpty());
        assertTrue(envWithStringAll.length() > envWithString.length());
    }

    @Test
    void virtualEnvFromFilePathShouldConfiguredCorrectly() {
        ConfigurationLoader loader = new ConfigurationLoader(envWithPath);
        assertTrue(checkConfigurationProps(loader));
    }

    @Test
    void virtualEnvWithFullConfigurationShouldCorrect() {
        ConfigurationLoader loader = new ConfigurationLoader(envWithStringAll);
        assertTrue(checkConfigurationProps(loader));
    }

    @Test
    void virtualEnvNotContainFullConfigurationShouldCorrect() {
        ConfigurationLoader loader = new ConfigurationLoader(envWithString);
        assertTrue(checkConfigurationProps(loader));
    }

    private boolean checkConfigurationProps(ConfigurationProperties properties) {
        return this.port.equals(properties.tcprepipePort()) && this.workingThreads.equals(properties.countWorkingThreads())
                && this.maxConnections.equals(properties.maxConnections()) && this.trafficCapacity.equals(properties.trafficCapacity())
                && this.encryptionUse.equals(properties.encryptionActive()) && this.encryptionAlgorithm.equals(properties.encryptionAlgorithm())
                && this.clientsProxy.equals(properties.clientsProxy()) && this.workingMode.equals(properties.workingMode());
    }
}
