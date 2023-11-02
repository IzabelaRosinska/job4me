package miwm.job4me.validators.entity.users;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.users.EmployeeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmployeeValidator {
    private final StringFieldValidator stringFieldValidator;

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final int MIN_FIRST_NAME_LENGTH = 2;
    private final int MIN_LAST_NAME_LENGTH = 2;
    private final int MIN_EMAIL_LENGTH = 2;

    private final int MAX_FIRST_NAME_LENGTH = 100;
    private final int MAX_LAST_NAME_LENGTH = 100;
    private final int MAX_EMAIL_LENGTH = 100;
    private final int MAX_PHONE_NUMBER_LENGTH = 20;

    private final int MAX_COUNT_OF_EDUCATION = 10;
    private final int MAX_COUNT_OF_EXPERIENCE = 10;
    private final int MAX_COUNT_OF_PROJECTS = 10;
    private final int MAX_COUNT_OF_SKILLS = 20;

    private final int MAX_LENGTH_OF_EDUCATION_DESCRIPTION = 100;
    private final int MAX_LENGTH_OF_EXPERIENCE_DESCRIPTION = 255;
    private final int MAX_LENGTH_OF_PROJECT_DESCRIPTION = 500;
    private final int MAX_LENGTH_OF_SKILL_DESCRIPTION = 50;

    private final String ENTITY_NAME = "Employee";

    public EmployeeValidator(StringFieldValidator stringFieldValidator) {
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validateForUpdateDto(EmployeeDto employeeDto) {
        validateNotNullEmployee(employeeDto);
        stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        validateEmail(employeeDto.getEmail());
        validateTelephone(employeeDto.getTelephone());
        validateListSizeAndElemLength(employeeDto.getEducation(), "education", MAX_COUNT_OF_EDUCATION, MAX_LENGTH_OF_EDUCATION_DESCRIPTION);
        validateListSizeAndElemLength(employeeDto.getExperience(), "experience", MAX_COUNT_OF_EXPERIENCE, MAX_LENGTH_OF_EXPERIENCE_DESCRIPTION);
        validateListSizeAndElemLength(employeeDto.getProjects(), "projects", MAX_COUNT_OF_PROJECTS, MAX_LENGTH_OF_PROJECT_DESCRIPTION);
        validateListSizeAndElemLength(employeeDto.getSkills(), "skills", MAX_COUNT_OF_SKILLS, MAX_LENGTH_OF_SKILL_DESCRIPTION);
    }

    private void validateNotNullEmployee(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            throw new InvalidArgumentException("Employee can not be null");
        }
    }

    private boolean validateEmailPattern(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void validateEmail(String email) {
        stringFieldValidator.validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(email, ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        if (!validateEmailPattern(email)) {
            throw new InvalidArgumentException(ExceptionMessages.mustMatchPattern(ENTITY_NAME, "email", EMAIL_PATTERN));
        }
    }

    private void validateTelephone(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.length() > MAX_PHONE_NUMBER_LENGTH) {
            throw new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "phoneNumber", MAX_PHONE_NUMBER_LENGTH));
        }
    }

    private void validateListSizeAndElemLength(ArrayList<String> list, String fieldName, int maxSize, int maxElemLength) {
        if (list != null && list.size() > maxSize) {
            throw new InvalidArgumentException(ExceptionMessages.listTooLong(ENTITY_NAME, fieldName, maxSize));
        }

        if (list != null) {
            for (String elem : list) {
                if (elem == null || elem.isEmpty()) {
                    throw new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(ENTITY_NAME, fieldName + " list element"));
                }

                if (elem.length() > maxElemLength) {
                    throw new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, fieldName, maxElemLength));
                }
            }
        }
    }
}
