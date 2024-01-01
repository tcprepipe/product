package org.deveugene.tcprepipe.configuration.validation;

public class PerformanceValidation extends Validation {
    private final String countWorkThreads, maxConnections, trafficCapacity;

    public PerformanceValidation(String countWorkThreads, String maxConnections, String trafficCapacity) {
        this.countWorkThreads = countWorkThreads;
        this.maxConnections = maxConnections;
        this.trafficCapacity = trafficCapacity;
    }

    public static int castParamToInt(String param) {
        try {
            if (!isInteger(param)) return -1;
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public boolean validateCountWorkThreads() {
        int value = castParamToInt(this.countWorkThreads);
        return value > 0 && value <= Short.MAX_VALUE;
    }

    public boolean validateMaxConnections() {
        int value = castParamToInt(this.maxConnections);
        return value > 0;
    }

    public boolean validateTrafficCapacity() {
        int value = castParamToInt(this.trafficCapacity);
        return value > 0;
    }

    @Override
    boolean validate() {
        return validateCountWorkThreads() && validateMaxConnections() && validateTrafficCapacity();
    }
}
