package org.deveugene.tcprepipe.configuration.loader;

import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class NetworkLoader extends Loader {
    public NetworkLoader(LoadingData loadingData) {
        super(loadingData);
    }

    @Override
    public void loadResources(Properties properties) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(getLoadingData().getValue()).openConnection();
            String props = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            StringReader propsIO = new StringReader(props);
            properties.load(propsIO);
        } catch (IOException e) {
            throw new FailLoadingDataException(getLoadingData());
        }
    }

    @Override
    public boolean isReady() {
        return getLoadingData().isPath();
    }
}
