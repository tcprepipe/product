package org.deveugene.tcprepipe.configuration.loader;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class SimpleLoader extends Loader {
    public SimpleLoader(LoadingData loadingData) {
        super(loadingData);
    }

    @Override
    public void loadResources(Properties properties) {
        String value = getLoadingData().getValue();
        StringReader valueIO = new StringReader(value);
        try {
            properties.load(valueIO);
        } catch (IOException e) {
            throw new FailLoadingDataException(getLoadingData());
        }
    }

    @Override
    public boolean isReady() {
        return getLoadingData().getOriginal() != null;
    }
}
