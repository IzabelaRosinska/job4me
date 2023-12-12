package miwm.job4me.validators.fields;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import org.springframework.stereotype.Component;

@Component
public class IntegerRangeValidator {
    public void validateSalaryRange(Integer salaryFrom, Integer salaryTo, String entityName) {
        if (salaryFrom == null) {
            throw new InvalidArgumentException(ExceptionMessages.notNull(entityName, "salaryFrom"));
        }

        validatePositive(salaryFrom, "salaryFrom", entityName);

        if (salaryTo != null) {
            validatePositive(salaryTo, "salaryTo", entityName);
            if (salaryFrom > salaryTo) {
                throw new InvalidArgumentException(ExceptionMessages.invalidRange(entityName, "salaryFrom", "salaryTo"));
            }
        }
    }

    private void validatePositive(Integer number, String fieldName, String entityName) {
        if (number <= 0) {
            throw new InvalidArgumentException(ExceptionMessages.mustBePositive(entityName, fieldName));
        }
    }
}
