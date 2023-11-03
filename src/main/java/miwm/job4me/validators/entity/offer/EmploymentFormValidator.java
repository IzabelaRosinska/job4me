package miwm.job4me.validators.entity.offer;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.EmploymentForm;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.EmploymentFormDto;
import org.springframework.stereotype.Component;

@Component
public class EmploymentFormValidator {
    private final IdValidator idValidator;
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_NAME_LENGTH = 1;
    private final int MAX_NAME_LENGTH = 25;
    private final String ENTITY_NAME = "EmploymentForm";

    public EmploymentFormValidator(IdValidator idValidator, StringFieldValidator stringFieldValidator) {
        this.idValidator = idValidator;
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validate(EmploymentForm employmentForm) {
        if (employmentForm == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        idValidator.validateLongId(employmentForm.getId(), ENTITY_NAME);
        stringFieldValidator.validateClassicStringRestrictedField(employmentForm.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }

    public void validateDto(EmploymentFormDto employmentForm) {
        if (employmentForm == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        idValidator.validateLongId(employmentForm.getId(), ENTITY_NAME);
        stringFieldValidator.validateClassicStringRestrictedField(employmentForm.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }
}
