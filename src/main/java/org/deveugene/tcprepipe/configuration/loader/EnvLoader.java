package org.deveugene.tcprepipe.configuration.loader;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class EnvLoader extends Loader {
    public EnvLoader(LoadingData loadingData) {
        super(loadingData);
    }

    @Override
    public void loadResources(Properties properties) {
        String env = System.getenv(getLoadingData().getOriginal());
        StringReader reader = new StringReader(env);
        try {
            properties.load(reader);
        } catch (IOException e) {
            throw new FailLoadingDataException(getLoadingData());
        }
    }

    @Override
    public boolean isReady() {
        return System.getenv(getLoadingData().getOriginal()) != null;
    }
}
