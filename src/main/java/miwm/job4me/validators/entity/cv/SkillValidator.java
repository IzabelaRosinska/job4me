package miwm.job4me.validators.entity.cv;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.web.model.cv.SkillDto;
import org.springframework.stereotype.Component;

@Component
public class SkillValidator {
    private final EmployeeValidator employeeValidator;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 50;
    private final String entityName = "Skill";

    public SkillValidator(EmployeeValidator employeeValidator) {
        this.employeeValidator = employeeValidator;
    }

    public void validateDto(SkillDto skill) {
        validateNotNullDto(skill);
        employeeValidator.validateEmployeeExistsById(skill.getEmployeeId());
        validateDescription(skill.getDescription());
    }

    public void validate(Skill skill) {
        validateNotNull(skill);
        employeeValidator.validateEmployeeExistsById(skill.getEmployee().getId());
        validateDescription(skill.getDescription());
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
        if (description.length() < MIN_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(ExceptionMessages.textTooShort(entityName, "description", MIN_DESCRIPTION_LENGTH));
        }

        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(ExceptionMessages.textTooLong(entityName, "description", MAX_DESCRIPTION_LENGTH));
        }
    }
}
