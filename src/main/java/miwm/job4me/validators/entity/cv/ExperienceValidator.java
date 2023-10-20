package miwm.job4me.validators.entity.cv;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.repositories.EmployeeRepository;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.web.model.cv.ExperienceDto;
import org.springframework.stereotype.Component;

@Component
public class ExperienceValidator {
    private EmployeeRepository employeeRepository;
    private IdValidator idValidator;
    private final int maxDescriptionLength = 255;
    private final String entityName = "Experience";

    public ExperienceValidator(EmployeeRepository employeeRepository, IdValidator idValidator) {
        this.employeeRepository = employeeRepository;
        this.idValidator = idValidator;
    }

    public void validateDto(ExperienceDto experience) {
        validateNotNullDto(experience);
        validateEmployeeExistsById(experience.getEmployeeId());
        validateDescription(experience.getDescription());
    }

    public void validate(Experience experience) {
        validateNotNull(experience);
        validateEmployeeExistsById(experience.getEmployee().getId());
        validateDescription(experience.getDescription());
    }

    private void validateEmployeeExistsById(Long id) {
        idValidator.validateLongId(id, entityName);
        employeeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ExceptionMessages.elementNotFound("Employee", id)));
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
        if (description.length() > maxDescriptionLength) {
            throw new IllegalArgumentException(ExceptionMessages.textTooLong(entityName, "description", maxDescriptionLength));
        }
    }
}
