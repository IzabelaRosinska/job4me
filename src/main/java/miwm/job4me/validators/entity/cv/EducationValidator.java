package miwm.job4me.validators.entity.cv;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Education;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.web.model.cv.EducationDto;
import org.springframework.stereotype.Component;

@Component
public class EducationValidator {
    private final EmployeeValidator employeeValidator;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 100;
    private final String entityName = "Education";

    public EducationValidator(EmployeeValidator employeeValidator) {
        this.employeeValidator = employeeValidator;
    }

    public void validateDto(EducationDto education) {
        validateNotNullDto(education);
        employeeValidator.validateEmployeeExistsById(education.getEmployeeId());
        validateDescription(education.getDescription());
    }

    public void validate(Education education) {
        validateNotNull(education);
        employeeValidator.validateEmployeeExistsById(education.getEmployee().getId());
        validateDescription(education.getDescription());
    }

    private void validateNotNullDto(EducationDto educationDto) {
        if (educationDto == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(entityName));
        }
    }

    private void validateNotNull(Education education) {
        if (education == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(entityName));
        }
    }

    private void validateDescription(String description) {
        if (description.length() < MIN_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(ExceptionMessages.textTooShort(entityName, "description", MIN_DESCRIPTION_LENGTH));
        }

        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(ExceptionMessages.textTooLong(entityName, "description", MAX_DESCRIPTION_LENGTH));
        }
    }
}
