package miwm.job4me.validators.entity.cv;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Education;
import miwm.job4me.repositories.EmployeeRepository;
import miwm.job4me.web.model.cv.EducationDto;
import org.springframework.stereotype.Component;

@Component
public class EducationValidator {
    private EmployeeRepository employeeRepository;
    private final int maxDescriptionLength = 100;
    private final String entityName = "Education";

    public EducationValidator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void validateDto(EducationDto education) {
        validateNotNullDto(education);
        validateEmployeeExistsById(education.getEmployeeId());
        validateDescription(education.getDescription());
    }

    public void validate(Education education) {
        validateNotNull(education);
        validateEmployeeExistsById(education.getEmployee().getId());
        validateDescription(education.getDescription());
    }

    private void validateEmployeeExistsById(Long id) {
        employeeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ExceptionMessages.elementNotFound("Employee", id)));
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
        if (description.length() > maxDescriptionLength) {
            throw new IllegalArgumentException(ExceptionMessages.textTooLong(entityName, "description", maxDescriptionLength));
        }
    }
}
