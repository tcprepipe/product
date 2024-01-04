package org.deveugene.tcprepipe.configuration;

public enum Key {
    PORT("tcprepipe.main.port", "35050"),
    WORKING_THREADS_COUNT("tcprepipe.perfomance.working.threads", "1"),
    MAX_CONNECTIONS("tcprepipe.performance.max.connections", "100"),
    TRAFFIC_CAPACITY("tcprepipe.performance.traffic.capacity", "167772160"),
    ENCRYPTION_USE("tcprepipe.encryption.use", "false"),
    ENCRYPTION_ALGORITHM("tcprepipe.encryption.algorithm", null),
    PORTS_PROXY_CLIENTS("tcprepipe.main.proxy.clients.ports", null),
    WORKING_MODE("tcprepipe.main.working.mode", "SERVER");

    public final String key;
    private final String def;

    Key(String key, String def) {
        this.key = key;
        this.def = def;
    }

    public String defOrNull() {
        return this.def;
    }

    public String defThrow() {
        if (this.def == null) throw new AppConfiguration.DefaultValueNotFoundException();
        return this.def;
    }
}
