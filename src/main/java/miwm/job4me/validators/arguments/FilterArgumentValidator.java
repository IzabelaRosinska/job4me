package miwm.job4me.validators.arguments;

import miwm.job4me.messages.ExceptionMessages;
import org.springframework.stereotype.Component;

@Component
public class FilterArgumentValidator {

    public void validateStringFilter(String filter, String entityName, String fieldName) {
        if (filter == null) {
            throw new IllegalArgumentException(ExceptionMessages.stringFilterNotNull(entityName, fieldName));
        }
    }
}
