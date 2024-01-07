package org.deveugene.tcprepipe.configuration.loader;

public interface LoadingData {
    boolean isPath();
    boolean isProperties();
    String getOriginal();
    String getValue();
}
