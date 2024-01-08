package org.deveugene.tcprepipe.configuration.loader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class SimpleLoaderTest {
    private static String TEMPLATE_FROM_PATH;
    private static final String TEMPLATE = "a=a\nb=b\nc=c";

    private final Map<String, String> PROPS = Map.of("a", "a", "b", "b", "c", "c");

    @BeforeAll
    static void init() throws IOException {
        Path tmp = Files.createTempFile("test", UUID.randomUUID().toString());
        try (OutputStream o = Files.newOutputStream(tmp)) {
            o.write(TEMPLATE.getBytes(StandardCharsets.UTF_8));
        }

        TEMPLATE_FROM_PATH = tmp.toAbsolutePath().toString();
    }

    @Test
    void propertiesFromStringShouldLoadCorrectly() {
        assertTrue(checkProps(TEMPLATE));
    }

    @Test
    void propertiesFromFileShouldLoadCorrectly() {
        assertTrue(checkProps(TEMPLATE_FROM_PATH));
    }

    private boolean checkProps(String template) {
        Loader loader = new SimpleLoader(new StringConfig(template));
        Properties properties = new Properties();
        loader.loadResources(properties);
        return PROPS.equals(properties);
    }

    @Test
    void loaderShouldReady() {
        Loader loader = new SimpleLoader(new StringConfig(TEMPLATE));
        assertTrue(loader.isReady());
    }

    @Test
    void loaderShouldNotReady() {
        Loader loader = new SimpleLoader(new StringConfig(null));
        assertFalse(loader.isReady());
    }
}
