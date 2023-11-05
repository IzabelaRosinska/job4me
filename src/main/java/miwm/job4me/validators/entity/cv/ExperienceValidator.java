package miwm.job4me.validators.entity.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.cv.ExperienceDto;
import org.springframework.stereotype.Component;

@Component
public class ExperienceValidator {
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 255;
    private final String ENTITY_NAME = "Experience";
    private final String DESCRIPTION_FIELD_NAME = "description";

    public ExperienceValidator(StringFieldValidator stringFieldValidator) {
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validateDto(ExperienceDto experience) {
        validateNotNullDto(experience);
        stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(experience.getDescription(), ENTITY_NAME, DESCRIPTION_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    public void validate(Experience experience) {
        validateNotNull(experience);
        stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(experience.getDescription(), ENTITY_NAME, DESCRIPTION_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    private void validateNotNullDto(ExperienceDto experienceDto) {
        if (experienceDto == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
    }

    private void validateNotNull(Experience experience) {
        if (experience == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
    }

}
