package miwm.job4me.services.users;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.services.cv.EducationService;
import miwm.job4me.services.cv.ExperienceService;
import miwm.job4me.services.cv.ProjectService;
import miwm.job4me.services.cv.SkillService;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.web.mappers.users.EmployeeMapper;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
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
    private IdValidator idValidator;
    @Mock
    private EmployeeValidator employeeValidator;
    @Mock
    private EmployeeMapper employeeMapper;
    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    private final String entityName = "Employee";
    private Employee employee;
    private EmployeeDto employeeDto;


    @BeforeEach
    public void setUp() {
        employee = Employee
                .builder()
                .id(1L)
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
        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), entityName);
        Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        EmployeeDto result = employeeServiceImpl.updateCV(employeeDto);

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
        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), entityName);
        Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        EmployeeDto result = employeeServiceImpl.updateCV(employeeDto);

        assertEquals(employeeDto, result);
    }

    @Test
    @DisplayName("Update CV info - invalid data, fail when EmployeeValidator fails")
    public void testUpdateCVFailWhenEmployeeValidatorFails() {
        employeeDto.setFirstName(null);

        Mockito.doThrow(new IllegalArgumentException(ExceptionMessages.notNullNotEmpty(entityName, "name"))).when(employeeValidator).validateForUpdateDto(employeeDto);

        try {
            employeeServiceImpl.updateCV(employeeDto);
        } catch (IllegalArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(entityName, "name"), e.getMessage());
        }
    }


}