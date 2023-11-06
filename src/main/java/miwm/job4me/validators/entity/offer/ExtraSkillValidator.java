package miwm.job4me.validators.entity.offer;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.ExtraSkill;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.ExtraSkillDto;
import org.springframework.stereotype.Component;

@Component
public class ExtraSkillValidator {
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 200;
    private final String ENTITY_NAME = "ExtraSkill";

    public ExtraSkillValidator(StringFieldValidator stringFieldValidator) {
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validateDto(ExtraSkillDto extraSkillDto) {
        if (extraSkillDto == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        stringFieldValidator.validateClassicStringRestrictedField(extraSkillDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    public void validate(ExtraSkill extraSkill) {
        if (extraSkill == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        stringFieldValidator.validateClassicStringRestrictedField(extraSkill.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }
}
