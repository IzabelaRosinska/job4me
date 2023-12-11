package miwm.job4me.validators.entity.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.EmploymentForm;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.EmploymentFormDto;
import org.springframework.stereotype.Component;

@Component
public class EmploymentFormValidator {
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_NAME_LENGTH = 1;
    private final int MAX_NAME_LENGTH = 25;
    private final String ENTITY_NAME = "EmploymentForm";

    public EmploymentFormValidator(StringFieldValidator stringFieldValidator) {
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validate(EmploymentForm employmentForm) {
        if (employmentForm == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        stringFieldValidator.validateClassicStringRestrictedField(employmentForm.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }

    public void validateDto(EmploymentFormDto employmentForm) {
        if (employmentForm == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        stringFieldValidator.validateClassicStringRestrictedField(employmentForm.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }
}
