package org.deveugene.tcprepipe.configuration.loader;

import lombok.Getter;

@Getter
public class FailLoadingDataException extends RuntimeException {
    private final LoadingData loadingData;

    public FailLoadingDataException(LoadingData loadingData) {
        super("Failed to load properties: '%s'.".formatted(loadingData.getValue()));
        this.loadingData = loadingData;
    }
}
