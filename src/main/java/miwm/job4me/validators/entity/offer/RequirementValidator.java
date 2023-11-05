package miwm.job4me.validators.entity.offer;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.Requirement;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.RequirementDto;
import org.springframework.stereotype.Component;

@Component
public class RequirementValidator {
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 250;
    private final String ENTITY_NAME = "Requirement";

    public RequirementValidator(StringFieldValidator stringFieldValidator) {
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validate(Requirement requirement) {
        if (requirement == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        stringFieldValidator.validateClassicStringRestrictedField(requirement.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    public void validateDto(RequirementDto requirement) {
        if (requirement == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        stringFieldValidator.validateClassicStringRestrictedField(requirement.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }
}
