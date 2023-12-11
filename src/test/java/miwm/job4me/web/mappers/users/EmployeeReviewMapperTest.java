package miwm.job4me.web.mappers.users;

import miwm.job4me.model.users.Employee;
import miwm.job4me.web.model.users.EmployeeReviewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

class EmployeeReviewMapperTest {

    @InjectMocks
    private EmployeeReviewMapper employeeReviewMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToDtoWhenCalledThenReturnEmployeeReviewDtoWithExpectedState() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setContactEmail("john.doe@example.com");
        employee.setTelephone("1234567890");
        employee.setAboutMe("Software Developer");
        employee.setInterests("Coding, Gaming");
        employee.setEducation(new HashSet<>());
        employee.setExperience(new HashSet<>());
        employee.setProjects(new HashSet<>());
        employee.setSkills(new HashSet<>());
        Boolean isSaved = true;

        when(employeeMapper.educationSetToStringList(employee.getEducation())).thenReturn(new ArrayList<>());
        when(employeeMapper.experienceSetToStringList(employee.getExperience())).thenReturn(new ArrayList<>());
        when(employeeMapper.projectsSetToStringList(employee.getProjects())).thenReturn(new ArrayList<>());
        when(employeeMapper.skillsSetToStringList(employee.getSkills())).thenReturn(new ArrayList<>());

        // Act
        EmployeeReviewDto result = employeeReviewMapper.toDto(employee, isSaved);

        // Assert
        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getLastName(), result.getLastName());
        assertEquals(employee.getContactEmail(), result.getEmail());
        assertEquals(employee.getTelephone(), result.getTelephone());
        assertEquals(employee.getAboutMe(), result.getAboutMe());
        assertEquals(employee.getInterests(), result.getInterests());
        assertEquals(isSaved, result.getIsSaved());
    }

    @Test
    void testToEntityWhenCalledThenReturnEmployeeWithExpectedState() {
        // Arrange
        EmployeeReviewDto employeeReviewDto = new EmployeeReviewDto();
        employeeReviewDto.setId(1L);
        employeeReviewDto.setFirstName("John");
        employeeReviewDto.setLastName("Doe");
        employeeReviewDto.setEmail("john.doe@example.com");
        employeeReviewDto.setTelephone("1234567890");
        employeeReviewDto.setAboutMe("Software Developer");
        employeeReviewDto.setInterests("Coding, Gaming");
        employeeReviewDto.setEducation(new ArrayList<>());
        employeeReviewDto.setExperience(new ArrayList<>());
        employeeReviewDto.setProjects(new ArrayList<>());
        employeeReviewDto.setSkills(new ArrayList<>());

        when(employeeMapper.stringListToEducationSet(employeeReviewDto.getEducation())).thenReturn(new HashSet<>());
        when(employeeMapper.stringListToExperienceSet(employeeReviewDto.getExperience())).thenReturn(new HashSet<>());
        when(employeeMapper.stringListToProjectsSet(employeeReviewDto.getProjects())).thenReturn(new HashSet<>());
        when(employeeMapper.stringListToSkillsSet(employeeReviewDto.getSkills())).thenReturn(new HashSet<>());

        // Act
        Employee result = employeeReviewMapper.toEntity(employeeReviewDto);

        // Assert
        assertNotNull(result);
        assertEquals(employeeReviewDto.getId(), result.getId());
        assertEquals(employeeReviewDto.getFirstName(), result.getFirstName());
        assertEquals(employeeReviewDto.getLastName(), result.getLastName());
        assertEquals(employeeReviewDto.getEmail(), result.getContactEmail());
        assertEquals(employeeReviewDto.getTelephone(), result.getTelephone());
        assertEquals(employeeReviewDto.getAboutMe(), result.getAboutMe());
        assertEquals(employeeReviewDto.getInterests(), result.getInterests());
    }
}
