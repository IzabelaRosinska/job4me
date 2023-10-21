package miwm.job4me.validators.entity.cv;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.web.model.cv.ExperienceDto;
import org.springframework.stereotype.Component;

@Component
public class ExperienceValidator {
    private final EmployeeValidator employeeValidator;
    private final IdValidator idValidator;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 255;
    private final String entityName = "Experience";

    public ExperienceValidator(EmployeeValidator employeeValidator, IdValidator idValidator) {
        this.employeeValidator = employeeValidator;
        this.idValidator = idValidator;
    }

    public void validateDto(ExperienceDto experience) {
        validateNotNullDto(experience);
        employeeValidator.validateEmployeeExistsById(experience.getEmployeeId());
        validateDescription(experience.getDescription());
    }

    public void validate(Experience experience) {
        validateNotNull(experience);
        employeeValidator.validateEmployeeExistsById(experience.getEmployee().getId());
        validateDescription(experience.getDescription());
    }

    private void validateNotNullDto(ExperienceDto experienceDto) {
        if (experienceDto == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(entityName));
        }
    }

    private void validateNotNull(Experience experience) {
        if (experience == null) {
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
