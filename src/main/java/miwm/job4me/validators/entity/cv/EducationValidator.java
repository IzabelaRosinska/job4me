package miwm.job4me.validators.entity.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Education;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.cv.EducationDto;
import org.springframework.stereotype.Component;

@Component
public class EducationValidator {
    private final EmployeeValidator employeeValidator;
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 100;
    private final String ENTITY_NAME = "Education";
    private final String DESCRIPTION_FIELD_NAME = "description";

    public EducationValidator(EmployeeValidator employeeValidator, StringFieldValidator stringFieldValidator) {
        this.employeeValidator = employeeValidator;
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validateDto(EducationDto education) {
        validateNotNullDto(education);
        employeeValidator.validateEmployeeExistsById(education.getEmployeeId());
        stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(education.getDescription(), ENTITY_NAME, DESCRIPTION_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    public void validate(Education education) {
        validateNotNull(education);
        employeeValidator.validateEmployeeExistsById(education.getEmployee().getId());
        stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(education.getDescription(), ENTITY_NAME, DESCRIPTION_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    private void validateNotNullDto(EducationDto educationDto) {
        if (educationDto == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
    }

    private void validateNotNull(Education education) {
        if (education == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
    }

}
