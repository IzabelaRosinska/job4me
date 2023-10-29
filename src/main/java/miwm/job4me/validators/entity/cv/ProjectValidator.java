package miwm.job4me.validators.entity.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Project;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.web.model.cv.ProjectDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectValidator {
    private final EmployeeValidator employeeValidator;
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 500;
    private final String ENTITY_NAME = "Project";

    public ProjectValidator(EmployeeValidator employeeValidator, StringFieldValidator stringFieldValidator) {
        this.employeeValidator = employeeValidator;
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validateDto(ProjectDto project) {
        validateNotNullDto(project);
        employeeValidator.validateEmployeeExistsById(project.getEmployeeId());
        stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(project.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    public void validate(Project project) {
        validateNotNull(project);
        employeeValidator.validateEmployeeExistsById(project.getEmployee().getId());
        stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(project.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    private void validateNotNullDto(ProjectDto projectDto) {
        if (projectDto == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
    }

    private void validateNotNull(Project project) {
        if (project == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
    }

}
