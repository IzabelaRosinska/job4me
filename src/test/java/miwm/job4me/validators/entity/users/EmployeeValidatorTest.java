package miwm.job4me.validators.entity.users;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.users.Employee;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.users.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private EmployeeValidator employeeValidator;
    private Employee employee;
    private EmployeeDto employeeDto;
    private final Long id = 1L;
    private final String ENTITY_NAME = "Employee";

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
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

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

        Mockito.doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, fieldName, MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);

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

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, fieldName, MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);

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

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, fieldName, MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

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

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);


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

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when education list is too long")
    public void validateForUpdateDtoInvalidTooLongEducationList() {
        String expectedMessage = ExceptionMessages.listTooLong(ENTITY_NAME, "education", MAX_COUNT_OF_EDUCATION);
        ArrayList<String> educationList = new ArrayList<String>(MAX_COUNT_OF_EDUCATION + 1);

        for (int i = 0; i < MAX_COUNT_OF_EDUCATION + 1; i++) {
            educationList.add("education " + i);
        }

        employeeDto.setEducation(educationList);

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when education list element is too long")
    public void validateForUpdateDtoInvalidTooLongEducationListElement() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "education", MAX_LENGTH_OF_EDUCATION_DESCRIPTION);
        ArrayList<String> educationList = new ArrayList<String>();
        educationList.add("a".repeat(MAX_LENGTH_OF_EDUCATION_DESCRIPTION + 1));
        employeeDto.setEducation(educationList);

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when education list element is null or empty")
    public void validateForUpdateDtoInvalidNullOrEmptyEducationListElement() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "education list element");
        ArrayList<String> educationList = new ArrayList<String>();
        educationList.add(null);
        employeeDto.setEducation(educationList);

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        educationList.set(0, "");
        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when experience list is too long")
    public void validateForUpdateDtoInvalidTooLongExperienceList() {
        String expectedMessage = ExceptionMessages.listTooLong(ENTITY_NAME, "experience", MAX_COUNT_OF_EXPERIENCE);
        ArrayList<String> experienceList = new ArrayList<String>(MAX_COUNT_OF_EXPERIENCE + 1);

        for (int i = 0; i < MAX_COUNT_OF_EXPERIENCE + 1; i++) {
            experienceList.add("experience " + i);
        }

        employeeDto.setExperience(experienceList);

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when experience list element is too long")
    public void validateForUpdateDtoInvalidTooLongExperienceListElement() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "experience", MAX_LENGTH_OF_EXPERIENCE_DESCRIPTION);
        ArrayList<String> experienceList = new ArrayList<String>();
        experienceList.add("a".repeat(MAX_LENGTH_OF_EXPERIENCE_DESCRIPTION + 1));
        employeeDto.setExperience(experienceList);

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when experience list element is null or empty")
    public void validateForUpdateDtoInvalidNullOrEmptyExperienceListElement() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "experience list element");
        ArrayList<String> experienceList = new ArrayList<String>();
        experienceList.add(null);
        employeeDto.setExperience(experienceList);

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        experienceList.set(0, "");
        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when projects list is too long")
    public void validateForUpdateDtoInvalidTooLongProjectsList() {
        String expectedMessage = ExceptionMessages.listTooLong(ENTITY_NAME, "projects", MAX_COUNT_OF_PROJECTS);
        ArrayList<String> projectsList = new ArrayList<String>(MAX_COUNT_OF_PROJECTS + 1);

        for (int i = 0; i < MAX_COUNT_OF_PROJECTS + 1; i++) {
            projectsList.add("project " + i);
        }

        employeeDto.setProjects(projectsList);

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when projects list element is too long")
    public void validateForUpdateDtoInvalidTooLongProjectsListElement() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "projects", MAX_LENGTH_OF_PROJECT_DESCRIPTION);
        ArrayList<String> projectsList = new ArrayList<String>();
        projectsList.add("a".repeat(MAX_LENGTH_OF_PROJECT_DESCRIPTION + 1));
        employeeDto.setProjects(projectsList);

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when projects list element is null or empty")
    public void validateForUpdateDtoInvalidNullOrEmptyProjectsListElement() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "projects list element");
        ArrayList<String> projectsList = new ArrayList<String>();
        projectsList.add(null);
        employeeDto.setProjects(projectsList);

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        projectsList.set(0, "");
        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when skills list is too long")
    public void validateForUpdateDtoInvalidTooLongSkillsList() {
        String expectedMessage = ExceptionMessages.listTooLong(ENTITY_NAME, "skills", MAX_COUNT_OF_SKILLS);
        ArrayList<String> skillsList = new ArrayList<String>(MAX_COUNT_OF_SKILLS + 1);

        for (int i = 0; i < MAX_COUNT_OF_SKILLS + 1; i++) {
            skillsList.add("skill " + i);
        }

        employeeDto.setSkills(skillsList);

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when skills list element is too long")
    public void validateForUpdateDtoInvalidTooLongSkillsListElement() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "skills", MAX_LENGTH_OF_SKILL_DESCRIPTION);
        ArrayList<String> skillsList = new ArrayList<String>();
        skillsList.add("a".repeat(MAX_LENGTH_OF_SKILL_DESCRIPTION + 1));
        employeeDto.setSkills(skillsList);

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate for update dto - fail validation when skills list element is null or empty")
    public void validateForUpdateDtoInvalidNullOrEmptySkillsListElement() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "skills list element");
        ArrayList<String> skillsList = new ArrayList<String>();
        skillsList.add(null);
        employeeDto.setSkills(skillsList);

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        try {
            employeeValidator.validateForUpdateDto(employeeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        skillsList.set(0, "");
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

        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getFirstName(), ENTITY_NAME, "firstName", MIN_FIRST_NAME_LENGTH, MAX_FIRST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getLastName(), ENTITY_NAME, "lastName", MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH);
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringNotNullNotEmptyRequiredFieldLengthRestrictions(employeeDto.getEmail(), ENTITY_NAME, "email", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH);

        assertDoesNotThrow(() -> employeeValidator.validateForUpdateDto(employeeDto));
    }
}
