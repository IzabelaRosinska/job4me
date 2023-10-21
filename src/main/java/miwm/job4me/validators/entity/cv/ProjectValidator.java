package miwm.job4me.validators.entity.cv;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Project;
import miwm.job4me.repositories.EmployeeRepository;
import miwm.job4me.web.model.cv.ProjectDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectValidator {
    private EmployeeRepository employeeRepository;
    private final int maxDescriptionLength = 500;
    private final String entityName = "Project";

    public ProjectValidator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void validateDto(ProjectDto project) {
        validateNotNullDto(project);
        validateEmployeeExistsById(project.getEmployeeId());
        validateDescription(project.getDescription());
    }

    public void validate(Project project) {
        validateNotNull(project);
        validateEmployeeExistsById(project.getEmployee().getId());
        validateDescription(project.getDescription());
    }

    private void validateEmployeeExistsById(Long id) {
        employeeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ExceptionMessages.elementNotFound("Employee", id)));
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
        if (description.length() > maxDescriptionLength) {
            throw new IllegalArgumentException(ExceptionMessages.textTooLong(entityName, "description", maxDescriptionLength));
        }
    }
}
