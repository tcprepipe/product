package org.deveugene.tcprepipe.configuration.parse;

import org.deveugene.tcprepipe.configuration.validation.IncorrectPortException;
import org.deveugene.tcprepipe.core.network.Port;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PortValueParserTest {
    @Test
    public void isPortShouldReturnTrueWhenCheckingPorts() {
        for (int i = 1; i <= PortValueParser.MAX_PORTS; i++) {
            if (i < PortValueParser.MAX_PORTS / 2) {
                assertTrue(PortValueParser.isPorts("%s-%s".formatted(i, PortValueParser.MAX_PORTS - i)));
            }
            assertTrue(PortValueParser.isPort(i));
            assertTrue(PortValueParser.isPorts(i + ""));
        }
    }

    @Test
    public void isPortShouldReturnFalseWhenCheckingPorts() {
        for (int i = 1; i <= PortValueParser.MAX_PORTS; i++) {
            if (i < PortValueParser.MAX_PORTS / 2) {
                assertFalse(PortValueParser.isPorts("%s-%s".formatted(1.125 * PortValueParser.MAX_PORTS - i, i)));
            }
            assertFalse(PortValueParser.isPort(i + PortValueParser.MAX_PORTS));
            assertFalse(PortValueParser.isPort(i - PortValueParser.MAX_PORTS));
        }

        assertFalse(PortValueParser.isPorts("-%s".formatted(PortValueParser.MAX_PORTS)));
        assertFalse(PortValueParser.isPorts("%s-".formatted(PortValueParser.MAX_PORTS)));
        assertFalse(PortValueParser.isPorts("%s-%s-%s".formatted(1, 2, 3)));
        assertFalse(PortValueParser.isPorts("%s-%s-%s-%s".formatted(1, 2, 3, 4)));
    }

    @Test
    public void creatingPortShouldThrowException() {
        assertThrows(IncorrectPortException.class, () -> PortValueParser.portOf(PortValueParser.MAX_PORTS + 1 + ""));
        assertThrows(IncorrectPortException.class, () -> PortValueParser.portOf("0"));
        assertThrows(IncorrectPortException.class, () -> PortValueParser.portOf("%s-%s".formatted(0, PortValueParser.MAX_PORTS + 1)));
        assertThrows(IncorrectPortException.class, () -> PortValueParser.portOf("%s-%s".formatted(PortValueParser.MAX_PORTS, PortValueParser.MAX_PORTS)));
        assertThrows(IncorrectPortException.class, () -> PortValueParser.portOf("%s-%s".formatted(PortValueParser.MAX_PORTS, PortValueParser.MAX_PORTS - 1)));
        assertThrows(IncorrectPortException.class, () -> PortValueParser.portOf("%s-%s,%s-%s".formatted(1, 2, 3, 4)));
        assertThrows(IncorrectPortException.class, () -> PortValueParser.portOf("%s-%s-%s".formatted(1, 2, 3)));
    }

    @Test
    public void getPortsWithoutDelimiterShouldReturnCorrectSizeArray() {
        PortValueParser parser = new PortValueParser("123,456,789");
        assertEquals(3, ((Collection<Port>) parser.getPorts()).size());
        parser = new PortValueParser("123,456,789,10000,50123");
        assertEquals(5, ((Collection<Port>) parser.getPorts()).size());
        parser = new PortValueParser("123-125,456-500,789-1000");
        assertEquals(3, ((Collection<Port>) parser.getPorts()).size());
        parser = new PortValueParser("1230,456-500,789-1000,50000-60000,650,45170");
        assertEquals(6, ((Collection<Port>) parser.getPorts()).size());
    }

    @Test
    public void getPortsWithinDelimiterShouldReturnCorrectSizeArray() {
        PortValueParser parser = new PortValueParser("123;456;789", ";");
        assertEquals(3, ((Collection<Port>) parser.getPorts()).size());
        parser = new PortValueParser("123'456-500'789'1000-1005", "'");
        assertEquals(4, ((Collection<Port>) parser.getPorts()).size());
        parser = new PortValueParser("1000 123-200 456-500 789-800 900", " ");
        assertEquals(5, ((Collection<Port>) parser.getPorts()).size());
    }

    @Test
    public void getPortsShouldThrowException() {
        PortValueParser parser = new PortValueParser("123 456,789");
        assertThrows(IncorrectPortException.class, parser::getPorts);
        parser = new PortValueParser("123456,789");
        assertThrows(IncorrectPortException.class, parser::getPorts);
        parser = new PortValueParser("123,456,789,-234,654,7642,346");
        assertThrows(IncorrectPortException.class, parser::getPorts);
        parser = new PortValueParser("123,456,789,500-495,110");
        assertThrows(IncorrectPortException.class, parser::getPorts);
        parser = new PortValueParser("123,456,789,0,90");
        assertThrows(IncorrectPortException.class, parser::getPorts);
    }

    @Test
    public void portShouldContainCorrectValue() {
        PortValueParser parser = new PortValueParser("80");
        Port port = parser.getPorts().iterator().next();
        assertEquals(80, port.getValue());
        assertFalse(port.isRange());
        assertThrows(RuntimeException.class, port::nextValue);
        assertFalse(port.hasNext());

        int increment = 80;
        parser = new PortValueParser("80-443");
        port = parser.getPorts().iterator().next();
        assertEquals(80, port.getValue());
        assertTrue(port.isRange());
        while (port.hasNext()) {
            assertEquals(++increment, port.nextValue());
        }
        assertEquals(443, increment);

        int count = 0;
        int[] ports = new int[9];
        parser = new PortValueParser("1-10");
        port = parser.getPorts().iterator().next();
        while (port.hasNext()) {
            ports[count] = port.nextValue();
            count++;
        }
        assertEquals(9, count);
        assertArrayEquals(new int[] {2, 3, 4, 5, 6, 7, 8, 9, 10}, ports);
    }

    @Test
    public void portValueParserShouldParsePortsCorrect() {
        int[][] correctValues = new int[][] {
                {443},
                {1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010},
                {500, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510},
                {900},
                {901},
                {902},
                {50000, 50001, 50002, 50003, 50004, 50005},
                {10}, {9}, {8}, {7}, {6}, {5}, {4}, {3}
        };
        PortValueParser parser = new PortValueParser("443,1000-1010,500-510,900,901,902,50000-50005,10,9,8,7,6,5,4,3");
        List<Port> ports = (List<Port>) parser.getPorts();
        for (int i = 0; i < ports.size(); i++) {
            Port port = ports.get(i);
            int value = correctValues[i][0];
            if (correctValues[i].length == 1) {
                assertFalse(port.isRange());
            } else {
                assertTrue(port.isRange());
                int count = 0;
                while (port.hasNext()) {
                    port.nextValue();
                    count++;
                }

                assertEquals(correctValues[i].length, count + 1 /* add getValue() : '+ 1' */);
            }

            assertEquals(value, port.getValue());
        }
    }

    @Test
    public void resetShouldResetIteratorCounterBeginning() {
        PortValueParser parser = new PortValueParser("100-200");
        Port port = parser.getPorts().iterator().next();
        for (int i = 101; i <= 200; i++) {
            int last = 0;
            for (int j = 100; j < i; j++) {
                last = port.nextValue();
            }

            assertEquals(i, last);
            port.reset();
        }
    }
}
