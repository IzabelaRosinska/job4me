package miwm.job4me.validators.fields;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ListValidator {
    private void validateProperElements(ArrayList<String> list, String fieldName, String entityName, int maxElemLength) {
        if (list != null) {
            for (String elem : list) {
                if (elem == null || elem.isEmpty()) {
                    throw new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(entityName, fieldName + " list element"));
                }

                if (elem.length() > maxElemLength) {
                    throw new InvalidArgumentException(ExceptionMessages.textTooLong(entityName, fieldName, maxElemLength));
                }
            }
        }
    }

    public void validateListSizeAndElemLength(ArrayList<String> list, String fieldName, String entityName, int maxSize, int maxElemLength) {
        if (list != null && list.size() > maxSize) {
            throw new InvalidArgumentException(ExceptionMessages.listTooLong(entityName, fieldName, maxSize));
        }

        validateProperElements(list, fieldName, entityName, maxElemLength);
    }

    public void validateRequiredListMinMaxSize(ArrayList<String> list, String fieldName, String entityName, int minSize, int maxSize, int maxElemLength) {
        if (list == null || list.isEmpty()) {
            throw new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(entityName, fieldName));
        }

        validateListSizeAndElemLength(list, fieldName, entityName, maxSize, maxElemLength);

        if (list.size() < minSize) {
            throw new InvalidArgumentException(ExceptionMessages.lengthOutOfRange(entityName, fieldName, minSize, maxSize));
        }
    }

    public void validateRequiredList(ArrayList<String> list, String fieldName, String entityName, int maxElemLength) {
        if (list == null || list.isEmpty()) {
            throw new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(entityName, fieldName));
        }

        validateProperElements(list, fieldName, entityName, maxElemLength);
    }
}
