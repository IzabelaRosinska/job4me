package miwm.job4me.validators.entity.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import org.springframework.stereotype.Component;

@Component
public class StringFieldValidator {
    public StringFieldValidator() {
    }

    public void validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(String description, String entityName, String descriptionFieldName, int minDescriptionLength, int maxDescriptionLength) {
        if (description == null || description.isEmpty()) {
            throw new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(descriptionFieldName, entityName));
        }

        if (description.length() < minDescriptionLength) {
            throw new InvalidArgumentException(ExceptionMessages.textTooShort(entityName, descriptionFieldName, minDescriptionLength));
        }

        if (description.length() > maxDescriptionLength) {
            throw new InvalidArgumentException(ExceptionMessages.textTooLong(entityName, descriptionFieldName, maxDescriptionLength));
        }
    }
}
