package org.deveugene.tcprepipe.configuration.loader;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Properties;

@Getter
@AllArgsConstructor
public abstract class Loader {
    private final LoadingData loadingData;

    abstract public void loadResources(Properties properties);
    abstract public boolean isReady();

    public static String getDefaultConfigName() {
        return "TCPREPIPE_CONFIG";
    }
}
