package org.deveugene.tcprepipe.configuration.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PerformanceValidationTest {
    private final PerformanceValidation validationSuccessful1 = new PerformanceValidation("10", "500", "1000");
    private final PerformanceValidation validationSuccessful2 = new PerformanceValidation("32000", "50000", "1000000");
    private final PerformanceValidation validationFail1 = new PerformanceValidation("0", "500", "1000");
    private final PerformanceValidation validationFail2 = new PerformanceValidation("10", "0", "1000");
    private final PerformanceValidation validationFail3 = new PerformanceValidation("10", "500", "0");
    private final PerformanceValidation validationFail4 = new PerformanceValidation("qwerty", "500", "1000");
    private final PerformanceValidation validationFail5 = new PerformanceValidation("10", "qwerty", "1000");
    private final PerformanceValidation validationFail6 = new PerformanceValidation("10", "500", "qwerty");
    private final PerformanceValidation validationFail7 = new PerformanceValidation("65536", "500", "1000");
    private final PerformanceValidation validationFail8 = new PerformanceValidation("10", "2147483648", "1000");
    private final PerformanceValidation validationFail9 = new PerformanceValidation("10", "500", "2147483648");

    @Test
    void validationShouldSuccessful() {
        assertTrue(validationSuccessful1.validate());
        assertTrue(validationSuccessful1.validateMaxConnections());
        assertTrue(validationSuccessful1.validateCountWorkThreads());
        assertTrue(validationSuccessful1.validateTrafficCapacity());
        assertTrue(validationSuccessful2.validate());
        assertTrue(validationSuccessful2.validateMaxConnections());
        assertTrue(validationSuccessful2.validateCountWorkThreads());
        assertTrue(validationSuccessful2.validateTrafficCapacity());
    }

    @Test
    void validationShouldFail() {
        assertFalse(validationFail1.validate());
        assertFalse(validationFail1.validateCountWorkThreads());
        assertTrue(validationFail1.validateTrafficCapacity());
        assertTrue(validationFail1.validateMaxConnections());
        assertFalse(validationFail2.validate());
        assertTrue(validationFail2.validateCountWorkThreads());
        assertFalse(validationFail2.validateMaxConnections());
        assertTrue(validationFail2.validateTrafficCapacity());
        assertFalse(validationFail3.validate());
        assertTrue(validationFail3.validateCountWorkThreads());
        assertTrue(validationFail3.validateMaxConnections());
        assertFalse(validationFail3.validateTrafficCapacity());
        assertFalse(validationFail4.validate());
        assertFalse(validationFail4.validateCountWorkThreads());
        assertTrue(validationFail4.validateMaxConnections());
        assertTrue(validationFail4.validateTrafficCapacity());
        assertFalse(validationFail5.validate());
        assertTrue(validationFail5.validateCountWorkThreads());
        assertFalse(validationFail5.validateMaxConnections());
        assertTrue(validationFail5.validateTrafficCapacity());
        assertFalse(validationFail6.validate());
        assertTrue(validationFail6.validateCountWorkThreads());
        assertTrue(validationFail6.validateMaxConnections());
        assertFalse(validationFail6.validateTrafficCapacity());
        assertFalse(validationFail7.validate());
        assertFalse(validationFail7.validateCountWorkThreads());
        assertTrue(validationFail7.validateMaxConnections());
        assertTrue(validationFail7.validateTrafficCapacity());
        assertFalse(validationFail8.validate());
        assertTrue(validationFail8.validateCountWorkThreads());
        assertFalse(validationFail8.validateMaxConnections());
        assertTrue(validationFail8.validateTrafficCapacity());
        assertFalse(validationFail9.validate());
        assertTrue(validationFail9.validateCountWorkThreads());
        assertTrue(validationFail9.validateMaxConnections());
        assertFalse(validationFail9.validateTrafficCapacity());
    }

    @Test
    void validateShouldNotThrowException() {
        validationSuccessful1.validateThrow();
        validationSuccessful2.validateThrow();
    }

    @Test
    void validateShouldThrowException() {
        assertThrows(ConfigValidationException.class, validationFail1::validateThrow);
        assertThrows(ConfigValidationException.class, validationFail2::validateThrow);
        assertThrows(ConfigValidationException.class, validationFail3::validateThrow);
        assertThrows(ConfigValidationException.class, validationFail4::validateThrow);
        assertThrows(ConfigValidationException.class, validationFail5::validateThrow);
        assertThrows(ConfigValidationException.class, validationFail6::validateThrow);
        assertThrows(ConfigValidationException.class, validationFail7::validateThrow);
        assertThrows(ConfigValidationException.class, validationFail8::validateThrow);
        assertThrows(ConfigValidationException.class, validationFail9::validateThrow);
    }
}
