package org.deveugene.tcprepipe.configuration.loader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StringConfig implements LoadingData {
    private final String data;

    public StringConfig(String data) {
        this.data = data;
    }

    @Override
    public boolean isPath() {
        try {
            Path path = Paths.get(this.data);
            if (path.isAbsolute() && Files.isRegularFile(path)) {
                return true;
            }
        } catch (InvalidPathException ignored) {}

        return false;
    }

    @Override
    public boolean isProperties() {
        return this.data.contains("=");
    }

    @Override
    public String getOriginal() {
        return this.data;
    }

    @Override
    public String getValue() {
        String value = this.data;
        if (isPath()) {
            try {
                value = Files.readString(Paths.get(this.data), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException("File not found.", e);
            }
        }

        return value;
    }
}
