package miwm.job4me.validators.entity.users;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.users.Employee;
import miwm.job4me.validators.fields.ListValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.users.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class EmployeeValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;
    @Mock
    private ListValidator listValidator;
    @InjectMocks
    private EmployeeValidator employeeValidator;
    private Employee employee;
    private EmployeeDto employeeDto;
    private final Long id = 1L;
    private final String ENTITY_NAME = "Employee";

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

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

    private final int MAX_LENGTH_OF_EDUCATION_DESCRIPTION = 150;
    private final int MAX_LENGTH_OF_EXPERIENCE_DESCRIPTION = 300;
    private final int MAX_LENGTH_OF_PROJECT_DESCRIPTION = 300;
    private final int MAX_LENGTH_OF_SKILL_DESCRIPTION = 100;

    @BeforeEach
    public void setUp() {
        employee = Employee.builder()
                .id(id)
                .email("userTest@wp.pl")
                .password("userTest")
                .userRole(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))
                .firstName("Jan")
                .lastName("Kowalski")
                .telephone("+48 123456789")
                .contactEmail("jan.kowalski@gmail.com")
                .aboutMe("Jestem studentem informatyki na Politechnice Wrocławskiej. Interesuje się programowaniem w Javie.")
                .interests("Programowanie, sport, muzyka")
                .build();

        employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setTelephone(employee.getTelephone());
        employeeDto.setEmail(employee.getContactEmail());
        employeeDto.setAboutMe(employee.getAboutMe());
        employeeDto.setInterests(employee.getInterests());
    }


    @Test
    @DisplayName("Validate for update dto - pas validation when valid employeeDto")
    public void validateForUpdateDtoValidEmployeeDto() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getEducation(), "education", ENTITY_NAME, MAX_COUNT_OF_EDUCATION, MAX_LENGTH_OF_EDUCATION_DESCRIPTION);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getExperience(), "experience", ENTITY_NAME, MAX_COUNT_OF_EXPERIENCE, MAX_LENGTH_OF_EXPERIENCE_DESCRIPTION);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getProjects(), "projects", ENTITY_NAME, MAX_COUNT_OF_PROJECTS, MAX_LENGTH_OF_PROJECT_DESCRIPTION);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getSkills(), "skills", ENTITY_NAME, MAX_COUNT_OF_SKILLS, MAX_LENGTH_OF_SKILL_DESCRIPTION);

        assertDoesNotThrow(() -> employeeValidator.validateForUpdateDto(employeeDto));
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when employeeDto is null")
    public void validateForUpdateDtoInvalidNullEmployeeDto() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            employeeValidator.validateForUpdateDto(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when StringFieldValidator fails (invalid firstName)")
    public void validateForUpdateDtoInvalidFirstName() {
        String fieldName = "firstName";
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, fieldName);
        employeeDto.setFirstName(null);

        doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getFirstName(), ENTITY_NAME, fieldName, MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when StringFieldValidator fails (invalid lastName)")
    public void validateForUpdateDtoInvalidLastName() {
        String fieldName = "lastName";
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, fieldName);
        employeeDto.setLastName(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getLastName(), ENTITY_NAME, fieldName, MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when StringFieldValidator fails (invalid email)")
    public void validateForUpdateDtoInvalidEmail() {
        String fieldName = "email";
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, fieldName);
        employeeDto.setEmail(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getEmail(), ENTITY_NAME, fieldName, MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when email doesn't match pattern")
    public void validateForUpdateDtoInvalidEmailDoesNotMatchPattern() {
        String expectedMessage = ExceptionMessages.mustMatchPattern(ENTITY_NAME, "email", EMAIL_PATTERN);
        employeeDto.setEmail("userTestwppl");

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);


        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        employeeDto.setEmail("userTestwp.pl");
        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        employeeDto.setEmail("userTest@wppl");
        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        employeeDto.setEmail("userTestwppl@");
        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        employeeDto.setEmail("@userTestwppl");
        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        employeeDto.setEmail("@userTestwppl@");
        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        employeeDto.setEmail("UserTest.spanko@com");
        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        employeeDto.setEmail("userTest@wp.p");
        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        employeeDto.setEmail("uestTEst@.com");
        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when telephone is too long")
    public void validateForUpdateDtoInvalidTooLongTelephone() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "phoneNumber", MAX_PHONE_NUMBER_LENGTH);
        employeeDto.setTelephone("a".repeat(MAX_PHONE_NUMBER_LENGTH + 1));

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when ListValidator fails (invalid education list)")
    public void validateForUpdateDtoInvalidTooLongEducationList() {
        String expectedMessage = ExceptionMessages.listTooLong(ENTITY_NAME, "education", MAX_COUNT_OF_EDUCATION);
        ArrayList<String> educationList = new ArrayList<String>(MAX_COUNT_OF_EDUCATION + 1);

        for (int i = 0; i < MAX_COUNT_OF_EDUCATION + 1; i++) {
            educationList.add("education " + i);
        }

        employeeDto.setEducation(educationList);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);
        doThrow(new InvalidArgumentException(expectedMessage)).when(listValidator).validateListSizeAndElemLength(employeeDto.getEducation(), "education", ENTITY_NAME, MAX_COUNT_OF_EDUCATION, MAX_LENGTH_OF_EDUCATION_DESCRIPTION);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when IdValidator fails (invalid experience list)")
    public void validateForUpdateDtoInvalidTooLongExperienceList() {
        String expectedMessage = ExceptionMessages.listTooLong(ENTITY_NAME, "experience", MAX_COUNT_OF_EXPERIENCE);
        ArrayList<String> experienceList = new ArrayList<String>(MAX_COUNT_OF_EXPERIENCE + 1);

        for (int i = 0; i < MAX_COUNT_OF_EXPERIENCE + 1; i++) {
            experienceList.add("experience " + i);
        }

        employeeDto.setExperience(experienceList);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getEducation(), "education", ENTITY_NAME, MAX_COUNT_OF_EDUCATION, MAX_LENGTH_OF_EDUCATION_DESCRIPTION);
        doThrow(new InvalidArgumentException(expectedMessage)).when(listValidator).validateListSizeAndElemLength(employeeDto.getExperience(), "experience", ENTITY_NAME, MAX_COUNT_OF_EXPERIENCE, MAX_LENGTH_OF_EXPERIENCE_DESCRIPTION);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());

        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when IdValidator fails (invalid projects list)")
    public void validateForUpdateDtoInvalidTooLongProjectsList() {
        String expectedMessage = ExceptionMessages.listTooLong(ENTITY_NAME, "projects", MAX_COUNT_OF_PROJECTS);
        ArrayList<String> projectsList = new ArrayList<String>(MAX_COUNT_OF_PROJECTS + 1);

        for (int i = 0; i < MAX_COUNT_OF_PROJECTS + 1; i++) {
            projectsList.add("project " + i);
        }

        employeeDto.setProjects(projectsList);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getEducation(), "education", ENTITY_NAME, MAX_COUNT_OF_EDUCATION, MAX_LENGTH_OF_EDUCATION_DESCRIPTION);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getExperience(), "experience", ENTITY_NAME, MAX_COUNT_OF_EXPERIENCE, MAX_LENGTH_OF_EXPERIENCE_DESCRIPTION);
        doThrow(new InvalidArgumentException(expectedMessage)).when(listValidator).validateListSizeAndElemLength(employeeDto.getProjects(), "projects", ENTITY_NAME, MAX_COUNT_OF_PROJECTS, MAX_LENGTH_OF_PROJECT_DESCRIPTION);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());

        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when IdValidator fails (invalid skills list)")
    public void validateForUpdateDtoInvalidTooLongSkillsList() {
        String expectedMessage = ExceptionMessages.listTooLong(ENTITY_NAME, "skills", MAX_COUNT_OF_SKILLS);
        ArrayList<String> skillsList = new ArrayList<String>(MAX_COUNT_OF_SKILLS + 1);

        for (int i = 0; i < MAX_COUNT_OF_SKILLS + 1; i++) {
            skillsList.add("skill " + i);
        }

        employeeDto.setSkills(skillsList);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getEducation(), "education", ENTITY_NAME, MAX_COUNT_OF_EDUCATION, MAX_LENGTH_OF_EDUCATION_DESCRIPTION);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getExperience(), "experience", ENTITY_NAME, MAX_COUNT_OF_EXPERIENCE, MAX_LENGTH_OF_EXPERIENCE_DESCRIPTION);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getProjects(), "projects", ENTITY_NAME, MAX_COUNT_OF_PROJECTS, MAX_LENGTH_OF_PROJECT_DESCRIPTION);
        doThrow(new InvalidArgumentException(expectedMessage)).when(listValidator).validateListSizeAndElemLength(employeeDto.getSkills(), "skills", ENTITY_NAME, MAX_COUNT_OF_SKILLS, MAX_LENGTH_OF_SKILL_DESCRIPTION);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());

        }
    }

    @Test
    @DisplayName("Validate for update dto - pass validation when optional fields are null")
    public void validateForUpdateDtoValidNullOptionalFields() {
        employeeDto.setTelephone(null);
        employeeDto.setAboutMe(null);
        employeeDto.setInterests(null);
        employeeDto.setEducation(null);
        employeeDto.setExperience(null);
        employeeDto.setProjects(null);
        employeeDto.setSkills(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getEducation(), "education", ENTITY_NAME, MAX_COUNT_OF_EDUCATION, MAX_LENGTH_OF_EDUCATION_DESCRIPTION);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getExperience(), "experience", ENTITY_NAME, MAX_COUNT_OF_EXPERIENCE, MAX_LENGTH_OF_EXPERIENCE_DESCRIPTION);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getProjects(), "projects", ENTITY_NAME, MAX_COUNT_OF_PROJECTS, MAX_LENGTH_OF_PROJECT_DESCRIPTION);
        doNothing().when(listValidator).validateListSizeAndElemLength(employeeDto.getSkills(), "skills", ENTITY_NAME, MAX_COUNT_OF_SKILLS, MAX_LENGTH_OF_SKILL_DESCRIPTION);


        assertDoesNotThrow(() -> employeeValidator.validateForUpdateDto(employeeDto));
    }
}
