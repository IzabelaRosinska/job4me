package miwm.job4me.validators.fields;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import org.springframework.stereotype.Component;

@Component
public class IdValidator {
    public void validateLongId(Long id, String className) {
        if (id == null) {
            throw new InvalidArgumentException(ExceptionMessages.idCannotBeNull(className));
        }
    }
}
