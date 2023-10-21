package miwm.job4me.validators.entity.cv;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Project;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.web.model.cv.ProjectDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectValidator {
    private final EmployeeValidator employeeValidator;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 500;
    private final String entityName = "Project";

    public ProjectValidator(EmployeeValidator employeeValidator) {
        this.employeeValidator = employeeValidator;
    }

    public void validateDto(ProjectDto project) {
        validateNotNullDto(project);
        employeeValidator.validateEmployeeExistsById(project.getEmployeeId());
        validateDescription(project.getDescription());
    }

    public void validate(Project project) {
        validateNotNull(project);
        employeeValidator.validateEmployeeExistsById(project.getEmployee().getId());
        validateDescription(project.getDescription());
    }

    private void validateNotNullDto(ProjectDto projectDto) {
        if (projectDto == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(entityName));
        }
    }

    private void validateNotNull(Project project) {
        if (project == null) {
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
