package miwm.job4me.validators.entity.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.Level;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.LevelDto;
import org.springframework.stereotype.Component;

@Component
public class LevelValidator {
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_NAME_LENGTH = 1;
    private final int MAX_NAME_LENGTH = 20;
    private final String ENTITY_NAME = "Level";

    public LevelValidator(StringFieldValidator stringFieldValidator) {
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validate(Level level) {
        if (level == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        stringFieldValidator.validateClassicStringRestrictedField(level.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }

    public void validateDto(LevelDto level) {
        if (level == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        stringFieldValidator.validateClassicStringRestrictedField(level.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }

}
