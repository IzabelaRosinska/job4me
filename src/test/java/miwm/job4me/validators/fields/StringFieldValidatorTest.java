package miwm.job4me.validators.fields;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StringFieldValidatorTest {
    @InjectMocks
    private StringFieldValidator stringFieldValidator;

    private final String ENTITY_NAME = "Test";
    private final String STRING_FIELD_NAME = "StringField";

    @Test
    @DisplayName("test validateClassicStringRestrictedField - pass validation when StringField is valid")
    void validateStringFieldValidStringField() {
        assertDoesNotThrow(() -> stringFieldValidator.validateClassicStringRestrictedField("test", ENTITY_NAME,
                STRING_FIELD_NAME, 1, 100));
    }

    @Test
    @DisplayName("test validateClassicStringRestrictedField - fail validation when StringField is null or empty")
    void validateStringFieldNullOrEmptyStringField() {
        try {
            stringFieldValidator.validateClassicStringRestrictedField(null, ENTITY_NAME, STRING_FIELD_NAME, 1, 100);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(STRING_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }

        try {
            stringFieldValidator.validateClassicStringRestrictedField("", ENTITY_NAME, STRING_FIELD_NAME, 1, 100);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(STRING_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateClassicStringRestrictedField - fail validation when StringField is too short")
    void validateStringFieldTooShortStringField() {
        int minStringFieldLength = 2;
        String StringField = "a".repeat(minStringFieldLength - 1);

        try {
            stringFieldValidator.validateClassicStringRestrictedField(StringField, ENTITY_NAME, STRING_FIELD_NAME, minStringFieldLength, 100);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.textTooShort(ENTITY_NAME, STRING_FIELD_NAME, minStringFieldLength), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateClassicStringRestrictedField - fail validation when StringField is too long")
    void validateStringFieldTooLongStringField() {
        int maxStringFieldLength = 20;
        String StringField = "a".repeat(maxStringFieldLength + 1);

        try {
            stringFieldValidator.validateClassicStringRestrictedField(StringField, ENTITY_NAME, STRING_FIELD_NAME, 1, maxStringFieldLength);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.textTooLong(ENTITY_NAME, STRING_FIELD_NAME, maxStringFieldLength), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateNotNullNotEmpty - pass validation when StringField is valid")
    void validateNotNullNotEmptyValidStringField() {
        assertDoesNotThrow(() -> stringFieldValidator.validateNotNullNotEmpty("test", ENTITY_NAME, STRING_FIELD_NAME));
    }

    @Test
    @DisplayName("test validateNotNullNotEmpty - fail validation when StringField is null")
    void validateNotNullNotEmptyNullOrEmptyStringField() {
        try {
            stringFieldValidator.validateNotNullNotEmpty(null, ENTITY_NAME, STRING_FIELD_NAME);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(STRING_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateNotNullNotEmpty - fail validation when StringField is empty")
    void validateNotNullNotEmptyEmptyStringField() {
        try {
            stringFieldValidator.validateNotNullNotEmpty("", ENTITY_NAME, STRING_FIELD_NAME);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(STRING_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

}
