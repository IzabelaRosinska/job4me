package miwm.job4me.validators.entity.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.cv.SkillDto;
import org.springframework.stereotype.Component;

@Component
public class SkillValidator {
    private final EmployeeValidator employeeValidator;
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 50;
    private final String ENTITY_NAME = "Skill";

    public SkillValidator(EmployeeValidator employeeValidator, StringFieldValidator stringFieldValidator) {
        this.employeeValidator = employeeValidator;
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validateDto(SkillDto skill) {
        validateNotNullDto(skill);
        employeeValidator.validateEmployeeExistsById(skill.getEmployeeId());
        stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(skill.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    public void validate(Skill skill) {
        validateNotNull(skill);
        employeeValidator.validateEmployeeExistsById(skill.getEmployee().getId());
        stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(skill.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    private void validateNotNullDto(SkillDto skillDto) {
        if (skillDto == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
    }

    private void validateNotNull(Skill skill) {
        if (skill == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
    }

}
