package miwm.job4me.validators.fields;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import org.springframework.stereotype.Component;

@Component
public class StringFieldValidator {
    public StringFieldValidator() {
    }

    public void validateClassicStringRestrictedField(String field, String entityName, String descriptionFieldName, int minDescriptionLength, int maxDescriptionLength) {
        if (field == null || field.isEmpty()) {
            throw new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(descriptionFieldName, entityName));
        }

        if (field.length() < minDescriptionLength) {
            throw new InvalidArgumentException(ExceptionMessages.textTooShort(entityName, descriptionFieldName, minDescriptionLength));
        }

        if (field.length() > maxDescriptionLength) {
            throw new InvalidArgumentException(ExceptionMessages.textTooLong(entityName, descriptionFieldName, maxDescriptionLength));
        }
    }
}
