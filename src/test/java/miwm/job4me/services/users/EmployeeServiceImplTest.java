package miwm.job4me.services.users;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Education;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.cv.Project;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.services.cv.EducationService;
import miwm.job4me.services.cv.ExperienceService;
import miwm.job4me.services.cv.ProjectService;
import miwm.job4me.services.cv.SkillService;
import miwm.job4me.services.recommendation.RecommendationService;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.users.EmployeeMapper;
import miwm.job4me.web.model.cv.EducationDto;
import miwm.job4me.web.model.cv.ExperienceDto;
import miwm.job4me.web.model.cv.ProjectDto;
import miwm.job4me.web.model.cv.SkillDto;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private UserAuthenticationService userAuthenticationService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EducationService educationService;
    @Mock
    private ExperienceService experienceService;
    @Mock
    private ProjectService projectService;
    @Mock
    private SkillService skillService;
    @Mock
    private RecommendationService recommendationService;
    @Mock
    private IdValidator idValidator;
    @Mock
    private EmployeeValidator employeeValidator;
    @Mock
    private EmployeeMapper employeeMapper;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private final String ENTITY_NAME = "Employee";
    private final Long ID = 1L;
    private Employee employee;
    private EmployeeDto employeeDto;


    @BeforeEach
    public void setUp() {
        employee = Employee
                .builder()
                .id(ID)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jankowalski@gmail.com")
                .password("password")
                .userRole(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))
                .contactEmail("jankowalski@gmail.com")
                .telephone("123456789")
                .education(null)
                .experience(null)
                .projects(null)
                .skills(null)
                .aboutMe("aboutMe")
                .interests("interests")
                .build();

        employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getContactEmail());
        employeeDto.setTelephone(employee.getTelephone());
        employeeDto.setEducation(null);
        employeeDto.setExperience(null);
        employeeDto.setProjects(null);
        employeeDto.setSkills(null);
        employeeDto.setAboutMe(employee.getAboutMe());
        employeeDto.setInterests(employee.getInterests());
    }

    @Test
    @DisplayName("check if employee exists by id - pass validation when valid id (not null) and employee exists in database")
    public void validateEmployeeExistsByIdEmployeeExists() {
        Mockito.doNothing().when(idValidator).validateLongId(ID, ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(ID)).thenReturn(true);

        assertTrue(employeeService.existsById(ID));
    }

    @Test
    @DisplayName("check if employee exists by id - fail validation when idValidator fails (invalid id - null)")
    public void validateEmployeeExistsByIdInvalidNullId() {
        Long nullId = null;
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        Mockito.doThrow(new InvalidArgumentException(expectedMessage)).when(idValidator).validateLongId(nullId, ENTITY_NAME);

        try {
            employeeService.existsById(nullId);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("check if employee exists by id - fail validation when valid id but employee doesn't exist in database")
    public void validateEmployeeExistsByIdEmployeeDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, ID);

        Mockito.doNothing().when(idValidator).validateLongId(ID, ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(ID)).thenReturn(false);

        assertFalse(employeeService.existsById(ID));
    }

//    @Test
//    @DisplayName("find current employee - pass validation when employee exists in database")
//    public void validateFindCurrentEmployeeEmployeeExists() {
//        Mockito.when(employeeService.getAuthEmployee()).thenReturn(employee);
//        Mockito.when(employeeMapper.toDto(employee)).thenReturn(employeeDto);
//        Mockito.when(employeeRepository.findById(ID)).thenReturn(Optional.of(employee));
//
//        assertDoesNotThrow(() -> employeeService.findCurrentEmployee());
//    }
//
//    @Test
//    @DisplayName("find current employee - fail validation when employee doesn't exist in database")
//    public void validateFindCurrentEmployeeEmployeeDoesNotExist() {
//        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, ID);
//
//        Mockito.when(userAuthenticationService.getAuthenticatedUser()).thenReturn(employee);
//        Mockito.when(employeeRepository.findById(ID)).thenReturn(Optional.empty());
//
//        try {
//            employeeService.findCurrentEmployee();
//            fail();
//        } catch (NoSuchElementFoundException e) {
//            assertEquals(expectedMessage, e.getMessage());
//        }
//    }

    @Test
    @DisplayName("save education - pass validation when education is valid")
    public void validateSaveEducationValidEducation() {
        Education education = Education.builder().id(1L).description("description").employee(employee).build();
        EducationDto educationDto = new EducationDto();
        educationDto.setId(education.getId());
        educationDto.setDescription(education.getDescription());
        educationDto.setEmployeeId(employee.getId());

        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        Mockito.when(educationService.save(education)).thenReturn(educationDto);

        assertEquals(educationDto, employeeService.saveEducation(education));
    }

    @Test
    @DisplayName("save education - fail validation when employee doesn't exist in database")
    public void validateSaveEducationEmployeeDoesNotExist() {
        Education education = Education.builder().id(1L).description("description").employee(employee).build();
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, employee.getId());

        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(false);

        try {
            employeeService.saveEducation(education);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save education - fail validation when education is null")
    public void validateSaveEducationNullEducation() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            employeeService.saveEducation(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save education - fail validation when IdValidator fails (invalid id - null)")
    public void validateSaveEducationInvalidNullId() {
        Education education = Education.builder().id(null).description("description").employee(employee).build();
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        Mockito.doThrow(new InvalidArgumentException(expectedMessage)).when(idValidator).validateLongId(education.getEmployee().getId(), ENTITY_NAME);

        try {
            employeeService.saveEducation(education);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save education - fail validation when Education.save fails")
    public void validateSaveEducationEducationSaveFails() {
        Education education = Education.builder().id(1L).description("description").employee(employee).build();
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        Mockito.when(educationService.save(education)).thenThrow(new InvalidArgumentException(expectedMessage));

        try {
            employeeService.saveEducation(education);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save experience - pass validation when experience is valid")
    public void validateSaveExperienceValidExperience() {
        Experience experience = Experience.builder().id(1L).description("description").employee(employee).build();
        ExperienceDto experienceDto = new ExperienceDto();
        experienceDto.setId(experience.getId());
        experienceDto.setDescription(experience.getDescription());
        experienceDto.setEmployeeId(employee.getId());

        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        Mockito.when(experienceService.save(experience)).thenReturn(experienceDto);

        assertEquals(experienceDto, employeeService.saveExperience(experience));
    }

    @Test
    @DisplayName("save experience - fail validation when employee doesn't exist in database")
    public void validateSaveExperienceEmployeeDoesNotExist() {
        Experience experience = Experience.builder().id(1L).description("description").employee(employee).build();
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, employee.getId());

        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(false);

        try {
            employeeService.saveExperience(experience);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save experience - fail validation when experience is null")
    public void validateSaveExperienceNullExperience() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            employeeService.saveExperience(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save experience - fail validation when IdValidator fails (invalid id - null)")
    public void validateSaveExperienceInvalidNullId() {
        Experience experience = Experience.builder().id(null).description("description").employee(employee).build();
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        Mockito.doThrow(new InvalidArgumentException(expectedMessage)).when(idValidator).validateLongId(experience.getEmployee().getId(), ENTITY_NAME);

        try {
            employeeService.saveExperience(experience);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save experience - fail validation when Experience.save fails")
    public void validateSaveExperienceExperienceSaveFails() {
        Experience experience = Experience.builder().id(1L).description("description").employee(employee).build();
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        Mockito.when(experienceService.save(experience)).thenThrow(new InvalidArgumentException(expectedMessage));

        try {
            employeeService.saveExperience(experience);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save project - pass validation when project is valid")
    public void validateSaveProjectValidProject() {
        Project project = Project.builder().id(1L).description("description").employee(employee).build();
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setDescription(project.getDescription());
        projectDto.setEmployeeId(employee.getId());

        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        Mockito.when(projectService.save(project)).thenReturn(projectDto);

        assertEquals(projectDto, employeeService.saveProject(project));
    }

    @Test
    @DisplayName("save project - fail validation when employee doesn't exist in database")
    public void validateSaveProjectEmployeeDoesNotExist() {
        Project project = Project.builder().id(1L).description("description").employee(employee).build();
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, employee.getId());

        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(false);

        try {
            employeeService.saveProject(project);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save project - fail validation when project is null")
    public void validateSaveProjectNullProject() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            employeeService.saveProject(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save project - fail validation when IdValidator fails (invalid id - null)")
    public void validateSaveProjectInvalidNullId() {
        Project project = Project.builder().id(null).description("description").employee(employee).build();
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        Mockito.doThrow(new InvalidArgumentException(expectedMessage)).when(idValidator).validateLongId(project.getEmployee().getId(), ENTITY_NAME);

        try {
            employeeService.saveProject(project);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save project - fail validation when Project.save fails")
    public void validateSaveProjectProjectSaveFails() {
        Project project = Project.builder().id(1L).description("description").employee(employee).build();
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        Mockito.when(projectService.save(project)).thenThrow(new InvalidArgumentException(expectedMessage));

        try {
            employeeService.saveProject(project);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save skill - pass validation when skill is valid")
    public void validateSaveSkillValidSkill() {
        Skill skill = Skill.builder().id(1L).description("description").employee(employee).build();
        SkillDto skillDto = new SkillDto();
        skillDto.setId(skill.getId());
        skillDto.setDescription(skill.getDescription());
        skillDto.setEmployeeId(employee.getId());

        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        Mockito.when(skillService.save(skill)).thenReturn(skillDto);

        assertEquals(skillDto, employeeService.saveSkill(skill));
    }

    @Test
    @DisplayName("save skill - fail validation when employee doesn't exist in database")
    public void validateSaveSkillEmployeeDoesNotExist() {
        Skill skill = Skill.builder().id(1L).description("description").employee(employee).build();
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, employee.getId());

        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(false);

        try {
            employeeService.saveSkill(skill);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save skill - fail validation when skill is null")
    public void validateSaveSkillNullSkill() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            employeeService.saveSkill(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save skill - fail validation when IdValidator fails (invalid id - null)")
    public void validateSaveSkillInvalidNullId() {
        Skill skill = Skill.builder().id(null).description("description").employee(employee).build();
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        Mockito.doThrow(new InvalidArgumentException(expectedMessage)).when(idValidator).validateLongId(skill.getEmployee().getId(), ENTITY_NAME);

        try {
            employeeService.saveSkill(skill);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("save skill - fail validation when Skill.save fails")
    public void validateSaveSkillSkillSaveFails() {
        Skill skill = Skill.builder().id(1L).description("description").employee(employee).build();
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        Mockito.when(skillService.save(skill)).thenThrow(new InvalidArgumentException(expectedMessage));

        try {
            employeeService.saveSkill(skill);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Update CV info - valid data, no CV info outside Employee table")
    public void testUpdateCVOnlyEmployeeTable() {
        Mockito.doNothing().when(employeeValidator).validateForUpdateDto(employeeDto);
        Mockito.when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        Mockito.when(employeeMapper.toDto(employee)).thenReturn(employeeDto);
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Mockito.doNothing().when(educationService).deleteAllByEmployeeId(employee.getId());
        Mockito.doNothing().when(experienceService).deleteAllByEmployeeId(employee.getId());
        Mockito.doNothing().when(projectService).deleteAllByEmployeeId(employee.getId());
        Mockito.doNothing().when(skillService).deleteAllByEmployeeId(employee.getId());
        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Mockito.doNothing().when(recommendationService).notifyUpdatedEmployee(employee.getId());

        EmployeeDto result = employeeService.updateCV(employeeDto);

        assertEquals(employeeDto, result);
    }

    @Test
    @DisplayName("Update CV info - valid data, CV info outside Employee table")
    public void testUpdateCVWithOutsideCVInfo() {
        ArrayList<String> education = new ArrayList<>();
        education.add("education1");
        education.add("education2");

        ArrayList<String> experience = new ArrayList<>();
        experience.add("experience1");
        experience.add("experience2");

        ArrayList<String> projects = new ArrayList<>();
        projects.add("project1");
        projects.add("project2");

        ArrayList<String> skills = new ArrayList<>();
        skills.add("skill1");
        skills.add("skill2");

        employeeDto.setEducation(education);
        employeeDto.setExperience(experience);
        employeeDto.setProjects(projects);
        employeeDto.setSkills(skills);

        Mockito.doNothing().when(employeeValidator).validateForUpdateDto(employeeDto);
        Mockito.when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        Mockito.when(employeeMapper.toDto(employee)).thenReturn(employeeDto);
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Mockito.doNothing().when(educationService).deleteAllByEmployeeId(employee.getId());
        Mockito.doNothing().when(experienceService).deleteAllByEmployeeId(employee.getId());
        Mockito.doNothing().when(projectService).deleteAllByEmployeeId(employee.getId());
        Mockito.doNothing().when(skillService).deleteAllByEmployeeId(employee.getId());
        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Mockito.doNothing().when(recommendationService).notifyUpdatedEmployee(employee.getId());

        EmployeeDto result = employeeService.updateCV(employeeDto);

        assertEquals(employeeDto, result);
    }

    @Test
    @DisplayName("Update CV info - invalid data, fail when EmployeeValidator fails")
    public void testUpdateCVFailWhenEmployeeValidatorFails() {
        employeeDto.setFirstName(null);

        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "name"))).when(employeeValidator).validateForUpdateDto(employeeDto);

        try {
            employeeService.updateCV(employeeDto);
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "name"), e.getMessage());
        }
    }
}
