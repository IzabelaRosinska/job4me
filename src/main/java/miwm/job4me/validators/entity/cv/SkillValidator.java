package miwm.job4me.validators.entity.cv;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.repositories.EmployeeRepository;
import miwm.job4me.web.model.cv.SkillDto;
import org.springframework.stereotype.Component;

@Component
public class SkillValidator {
    private EmployeeRepository employeeRepository;
    private final int maxDescriptionLength = 50;
    private final String entityName = "Skill";

    public SkillValidator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void validateDto(SkillDto skill) {
        validateNotNullDto(skill);
        validateEmployeeExistsById(skill.getEmployeeId());
        validateDescription(skill.getDescription());
    }

    public void validate(Skill skill) {
        validateNotNull(skill);
        validateEmployeeExistsById(skill.getEmployee().getId());
        validateDescription(skill.getDescription());
    }

    private void validateEmployeeExistsById(Long id) {
        employeeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ExceptionMessages.elementNotFound("Employee", id)));
    }

    private void validateNotNullDto(SkillDto skillDto) {
        if (skillDto == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(entityName));
        }
    }

    private void validateNotNull(Skill skill) {
        if (skill == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(entityName));
        }
    }

    private void validateDescription(String description) {
        if (description.length() > maxDescriptionLength) {
            throw new IllegalArgumentException(ExceptionMessages.textTooLong(entityName, "description", maxDescriptionLength));
        }
    }
}
