package org.deveugene.tcprepipe.configuration.loader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class StringConfigTest {
    private static final String FILE_IN = "Hello World!";
    private static LoadingData path;
    private final LoadingData stringProps = new StringConfig("a=a");
    private final LoadingData string = new StringConfig("hello");

    @BeforeAll
    static void init() throws IOException {
        Path tmp = Files.createTempFile("test", UUID.randomUUID().toString());
        try (OutputStream o = Files.newOutputStream(tmp)) {
            o.write(FILE_IN.getBytes(StandardCharsets.UTF_8));
        }

        path = new StringConfig(tmp.toAbsolutePath().toString());
    }

    @Test
    void loadedFileShouldOutputText() {
        assertTrue(path.isPath());
        assertFalse(path.isProperties());
        assertNotEquals(path.getOriginal(), path.getValue());
        assertEquals(FILE_IN, path.getValue());
    }

    @Test
    void textShouldContainProperties() {
        assertFalse(stringProps.isPath());
        assertTrue(stringProps.isProperties());
        assertEquals(stringProps.getOriginal(), stringProps.getValue());
        assertEquals("a=a", stringProps.getValue());
    }

    @Test
    void shouldContainText() {
        assertFalse(string.isPath());
        assertFalse(string.isProperties());
        assertEquals(string.getOriginal(), string.getValue());
        assertEquals("hello", string.getValue());
    }
}
