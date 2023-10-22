package miwm.job4me.validators.entity.users;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.web.model.users.EmployeeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EmployeeValidator {
    private final EmployeeRepository employeeRepository;
    private final IdValidator idValidator;

    private final int MIN_FIRST_NAME_LENGTH = 1;
    private final int MIN_LAST_NAME_LENGTH = 1;
    private final int MIN_EMAIL_LENGTH = 1;

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

    public EmployeeValidator(EmployeeRepository employeeRepository, IdValidator idValidator) {
        this.employeeRepository = employeeRepository;
        this.idValidator = idValidator;
    }

    public void validateEmployeeExistsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    public void validateForUpdateDto(EmployeeDto employeeDto) {
        validateEmployeeExistsById(employeeDto.getId());
        validateNotNullEmployee(employeeDto);
        validateClassicRequiredFieldLengthRestrictions(employeeDto.getFirstName(), "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        validateClassicRequiredFieldLengthRestrictions(employeeDto.getLastName(), "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        validateEmail(employeeDto.getEmail());
        validateTelephone(employeeDto.getTelephone());
        validateListSizeAndElemLength(employeeDto.getEducation(), "education", MAX_COUNT_OF_EDUCATION, MAX_LENGTH_OF_EDUCATION_DESCRIPTION);
        validateListSizeAndElemLength(employeeDto.getExperience(), "experience", MAX_COUNT_OF_EXPERIENCE, MAX_LENGTH_OF_EXPERIENCE_DESCRIPTION);
        validateListSizeAndElemLength(employeeDto.getProjects(), "projects", MAX_COUNT_OF_PROJECTS, MAX_LENGTH_OF_PROJECT_DESCRIPTION);
        validateListSizeAndElemLength(employeeDto.getSkills(), "skills", MAX_COUNT_OF_SKILLS, MAX_LENGTH_OF_SKILL_DESCRIPTION);
    }

    private void validateNotNullEmployee(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            throw new IllegalArgumentException("Employee can not be null");
        }
    }

    private void validateClassicRequiredFieldLengthRestrictions(String field, String fieldName, int minLength, int maxLength) {
        if (field == null || field.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.notNullNotEmpty(ENTITY_NAME, fieldName));
        }

        if (field.length() < minLength) {
            throw new IllegalArgumentException(ExceptionMessages.textTooShort(ENTITY_NAME, fieldName, minLength));
        }

        if (field.length() > maxLength) {
            throw new IllegalArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, fieldName, maxLength));
        }
    }

    private void validateEmail(String email) {
        validateClassicRequiredFieldLengthRestrictions(email, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        if (!email.contains("@")) {
            throw new IllegalArgumentException(ExceptionMessages.mustContain(ENTITY_NAME, "email", "@"));
        }
    }

    private void validateTelephone(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.length() > MAX_PHONE_NUMBER_LENGTH) {
            throw new IllegalArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "phoneNumber", MAX_PHONE_NUMBER_LENGTH));
        }
    }

    private void validateListSizeAndElemLength(ArrayList<String> list, String fieldName, int maxSize, int maxElemLength) {
        if (list != null && list.size() > maxSize) {
            throw new IllegalArgumentException(ExceptionMessages.listTooLong(ENTITY_NAME, fieldName, maxSize));
        }

        if (list != null) {
            for (String elem : list) {
                if (elem.length() > maxElemLength) {
                    throw new IllegalArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, fieldName, maxElemLength));
                }
            }
        }
    }
}
