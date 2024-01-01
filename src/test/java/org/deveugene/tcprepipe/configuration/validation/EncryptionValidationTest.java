package org.deveugene.tcprepipe.configuration.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EncryptionValidationTest {
    private final EncryptionValidation validationSuccessful1 = new EncryptionValidation("false", "HSA");
    private final EncryptionValidation validationSuccessful2 = new EncryptionValidation("true", "HSA");
    private final EncryptionValidation validationSuccessful3 = new EncryptionValidation("true", "RSA");
    private final EncryptionValidation validationSuccessful4 = new EncryptionValidation("false", "RSA");
    private final EncryptionValidation validationFail1 = new EncryptionValidation("0", "HSA");
    private final EncryptionValidation validationFail2 = new EncryptionValidation("1", "HSA");
    private final EncryptionValidation validationFail3 = new EncryptionValidation("qwerty", "HSA");
    private final EncryptionValidation validationFail4 = new EncryptionValidation("false", "MAC");
    private final EncryptionValidation validationFail5 = new EncryptionValidation("false", "hsa");

    @Test
    void validationShouldSuccessful() {
        assertTrue(validationSuccessful1.validate());
        assertTrue(validationSuccessful1.validateActive());
        assertTrue(validationSuccessful1.validateAlgorithm());
        assertTrue(validationSuccessful2.validate());
        assertTrue(validationSuccessful2.validateActive());
        assertTrue(validationSuccessful2.validateAlgorithm());
        assertTrue(validationSuccessful3.validate());
        assertTrue(validationSuccessful3.validateActive());
        assertTrue(validationSuccessful3.validateAlgorithm());
        assertTrue(validationSuccessful4.validate());
        assertTrue(validationSuccessful4.validateActive());
        assertTrue(validationSuccessful4.validateAlgorithm());
    }

    @Test
    void validationShouldFail() {
        assertFalse(validationFail1.validate());
        assertFalse(validationFail1.validateActive());
        assertTrue(validationFail1.validateAlgorithm());
        assertFalse(validationFail2.validate());
        assertFalse(validationFail2.validateActive());
        assertTrue(validationFail2.validateAlgorithm());
        assertFalse(validationFail3.validate());
        assertFalse(validationFail3.validateActive());
        assertTrue(validationFail3.validateAlgorithm());
        assertFalse(validationFail4.validate());
        assertTrue(validationFail4.validateActive());
        assertFalse(validationFail4.validateAlgorithm());
        assertFalse(validationFail5.validate());
        assertTrue(validationFail5.validateActive());
        assertFalse(validationFail5.validateAlgorithm());
    }
}
