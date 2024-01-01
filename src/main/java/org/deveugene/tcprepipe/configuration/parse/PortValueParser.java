package org.deveugene.tcprepipe.configuration.parse;

import org.deveugene.tcprepipe.core.network.Port;

import java.util.Arrays;
import java.util.List;

public class PortValueParser {
    public static final int MAX_PORTS = 65535;
    public static final char PORT_RANGE_SEPARATOR = '-';

    private final String value;
    private String delimiter = ",";

    public PortValueParser(String value) {
        this.value = value;
    }

    public PortValueParser(String value, String delimiter) {
        this.value = value;
        this.delimiter = delimiter;
    }

    public static Port portOf(String portValue) {
        if (!isPorts(portValue)) {
            throw new IncorrectPortException(portValue);
        }

        List<Integer> ports = Arrays.stream(portValue.split(PORT_RANGE_SEPARATOR + "")).map(Integer::parseInt).toList();
        for (Integer p : ports) {
            if (!isPort(p)) {
                throw new IncorrectPortException(String.valueOf(p));
            }
        }

        if (ports.size() == 1) {
            return new PortRange(ports.get(0));
        }

        return new PortRange(ports.get(0), ports.get(1));
    }

    public static boolean isPorts(String port) {
        int indexSeparator;
        return !port.isEmpty() && !port.isBlank()
                && ((indexSeparator = port.indexOf(PORT_RANGE_SEPARATOR)) >= 1 && port.lastIndexOf(PORT_RANGE_SEPARATOR) == indexSeparator && indexSeparator != port.length() - 1 || indexSeparator == -1)
                && port.matches("[\\d-]+");
    }

    public static boolean isPort(int value) {
        return value > 0 && value <= MAX_PORTS;
    }

    public Iterable<Port> getPorts() {
        return Arrays.stream(this.value.split(this.delimiter))
                .map(PortValueParser::portOf)
                .distinct()
                .toList();
    }

    private static class PortRange implements Port {
        private final int value, closingValue;
        private final boolean range;
        private int current;

        public PortRange(int value) {
            this.value = value;
            this.closingValue = 0;
            this.range = false;
            this.current = value;
        }

        public PortRange(int value, int closingValue) {
            if (value >= closingValue) {
                throw new IncorrectPortException("%s-%s".formatted(value, closingValue));
            }

            this.value = value;
            this.closingValue = closingValue;
            this.range = true;
            this.current = value;
        }

        @Override
        public int getValue() {
            return this.value;
        }

        @Override
        public boolean isRange() {
            return this.range;
        }

        @Override
        public Port reset() {
            this.current = this.value;
            return this;
        }

        public boolean hasNext() {
            return this.range && this.current + 1 <= this.closingValue;
        }

        public int nextValue() {
            if (this.current + 1 > this.closingValue || !this.range) {
                throw new RuntimeException("Stop iteration.");
            }

            return ++this.current;
        }
    }
}
