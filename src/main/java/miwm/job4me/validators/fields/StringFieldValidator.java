package miwm.job4me.validators.fields;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import org.springframework.stereotype.Component;

@Component
public class StringFieldValidator {
    public StringFieldValidator() {
    }

    public void validateClassicStringRestrictedField(String field, String entityName, String fieldName, int minLength, int maxLength) {
        if (field == null || field.isEmpty()) {
            throw new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(fieldName, entityName));
        }

        if (field.length() < minLength) {
            throw new InvalidArgumentException(ExceptionMessages.textTooShort(entityName, fieldName, minLength));
        }

        if (field.length() > maxLength) {
            throw new InvalidArgumentException(ExceptionMessages.textTooLong(entityName, fieldName, maxLength));
        }
    }

    public void validateNotNullNotEmpty(String field, String entityName, String fieldName) {
        if (field == null || field.isEmpty()) {
            throw new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(fieldName, entityName));
        }
    }
}
