package miwm.job4me.validators.entity;

import miwm.job4me.messages.ExceptionMessages;
import org.springframework.stereotype.Component;

@Component
public class IdValidator {
    public void validateLongId(Long id, String className) {
        if (id == null) {
            throw new IllegalArgumentException(ExceptionMessages.idCannotBeNull(className));
        }
    }
}
