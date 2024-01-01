package org.deveugene.tcprepipe.configuration.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainValidationTest {
    private final MainValidation validationSuccessful1 = new MainValidation("80", "SERVER");
    private final MainValidation validationSuccessful2 = new MainValidation("65000", "CLIENT");
    private final MainValidation validationFail1 = new MainValidation("69001", "SERVER");
    private final MainValidation validationFail2 = new MainValidation("69500", "CLIENT");
    private final MainValidation validationFail3 = new MainValidation("500", "CLIENTSERVER");
    private final MainValidation validationFail4 = new MainValidation("999", "QWERTY");
    private final MainValidation validationFail5 = new MainValidation("0", "SERVER");
    private final MainValidation validationFail6 = new MainValidation("-1", "SERVER");
    private final MainValidation validationFail7 = new MainValidation("i386", "SERVER");

    @Test
    void validationShouldSuccessful() {
        assertTrue(validationSuccessful1.validate());
        assertTrue(validationSuccessful1.validatePort());
        assertTrue(validationSuccessful1.validateMode());
        assertTrue(validationSuccessful2.validate());
        assertTrue(validationSuccessful2.validatePort());
        assertTrue(validationSuccessful2.validateMode());
    }

    @Test
    void validationShouldFail() {
        assertFalse(validationFail1.validate());
        assertFalse(validationFail1.validatePort());
        assertTrue(validationFail1.validateMode());
        assertFalse(validationFail2.validate());
        assertFalse(validationFail2.validatePort());
        assertTrue(validationFail2.validateMode());
        assertFalse(validationFail3.validate());
        assertTrue(validationFail3.validatePort());
        assertFalse(validationFail3.validateMode());
        assertFalse(validationFail4.validate());
        assertTrue(validationFail4.validatePort());
        assertFalse(validationFail4.validateMode());
        assertFalse(validationFail5.validate());
        assertFalse(validationFail5.validatePort());
        assertTrue(validationFail5.validateMode());
        assertFalse(validationFail6.validate());
        assertFalse(validationFail6.validatePort());
        assertTrue(validationFail6.validateMode());
        assertFalse(validationFail7.validate());
        assertFalse(validationFail7.validatePort());
        assertTrue(validationFail7.validateMode());
    }
}
