package miwm.job4me.validators.entity.offer;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.Requirement;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import org.springframework.stereotype.Component;

@Component
public class RequirementValidator {
    private final IdValidator idValidator;
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 250;
    private final String ENTITY_NAME = "Requirement";

    public RequirementValidator(IdValidator idValidator, StringFieldValidator stringFieldValidator) {
        this.idValidator = idValidator;
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validate(Requirement requirement) {
        if (requirement == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        idValidator.validateLongId(requirement.getId(), ENTITY_NAME);
        stringFieldValidator.validateClassicStringRestrictedField(requirement.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    public void validateDto(Requirement requirement) {
        if (requirement == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        idValidator.validateLongId(requirement.getId(), ENTITY_NAME);
        stringFieldValidator.validateClassicStringRestrictedField(requirement.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }
}
