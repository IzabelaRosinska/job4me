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
    @DisplayName("validate StringField - pass validation when StringField is valid")
    void validateStringFieldValidStringField() {
        assertDoesNotThrow(() -> stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions("test", ENTITY_NAME,
                STRING_FIELD_NAME, 1, 100));
    }

    @Test
    @DisplayName("validate StringField - fail validation when StringField is null or empty")
    void validateStringFieldNullOrEmptyStringField() {
        try {
            stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(null, ENTITY_NAME, STRING_FIELD_NAME, 1, 100);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(STRING_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }

        try {
            stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions("", ENTITY_NAME, STRING_FIELD_NAME, 1, 100);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(STRING_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("validate StringField - fail validation when StringField is too short")
    void validateStringFieldTooShortStringField() {
        int minStringFieldLength = 2;
        String StringField = "a".repeat(minStringFieldLength - 1);

        try {
            stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(StringField, ENTITY_NAME, STRING_FIELD_NAME, minStringFieldLength, 100);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.textTooShort(ENTITY_NAME, STRING_FIELD_NAME, minStringFieldLength), e.getMessage());
        }
    }

    @Test
    @DisplayName("validate StringField - fail validation when StringField is too long")
    void validateStringFieldTooLongStringField() {
        int maxStringFieldLength = 20;
        String StringField = "a".repeat(maxStringFieldLength + 1);

        try {
            stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(StringField, ENTITY_NAME, STRING_FIELD_NAME, 1, maxStringFieldLength);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.textTooLong(ENTITY_NAME, STRING_FIELD_NAME, maxStringFieldLength), e.getMessage());
        }
    }

}
