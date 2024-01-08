package org.deveugene.tcprepipe.configuration.loader;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

@Disabled
public class NetworkLoaderTest {
    private static ServerSocket server;
    private static final String PATH = "http://localhost:60105/";
    private static Thread thread;
    private static final String TEMPLATE = "a=a\nb=b\nc=c\n";
    private static Path tmp;

    private final Map<String, String> PROPS = Map.of("a", "a", "b", "b", "c", "c");

    @BeforeEach
    void init() throws IOException, InterruptedException {
        server = new ServerSocket(60105);
        String def = "HTTP/1.1 200 OK\n\n\n";
        thread = new Thread(() -> {
            try {
                while (true) {
                    Socket socket = server.accept();
                    socket.getOutputStream().write((def + TEMPLATE).getBytes(StandardCharsets.UTF_8));
                    socket.close();
                }
            } catch (IOException ignored) {}
        });
        thread.start();
        thread.join(2000);

        tmp = Files.createTempFile("test", UUID.randomUUID().toString());
        try (OutputStream o = Files.newOutputStream(tmp)) {
            o.write(PATH.getBytes(StandardCharsets.UTF_8));
        }
    }

    @AfterEach
    void close() throws IOException {
        thread.interrupt();
        server.close();
    }

    @Test
    @Order(1)
    void propertiesFromStringShouldLoadCorrectly() {
        assertTrue(checkProps(PATH));
    }

    @Test
    @Order(2)
    void propertiesFromFileShouldLoadCorrectly() {
        assertTrue(checkProps(tmp.toAbsolutePath().toString()));
    }

    private boolean checkProps(String value) {
        Loader loader = new NetworkLoader(new StringConfig(value));
        Properties properties = new Properties();
        loader.loadResources(properties);
        return PROPS.equals(properties);
    }
}
