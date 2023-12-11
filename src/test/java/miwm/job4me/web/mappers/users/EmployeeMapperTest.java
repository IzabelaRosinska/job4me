package miwm.job4me.web.mappers.users;

import miwm.job4me.model.cv.Education;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.cv.Project;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.model.users.Employee;
import miwm.job4me.web.model.users.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmployeeMapperTest {

    private EmployeeMapper employeeMapper;

    @BeforeEach
    public void setUp() {
        employeeMapper = new EmployeeMapper();
    }

    @Test
    public void testToDtoWhenValidEmployeeThenCorrectDto() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setContactEmail("john.doe@gmail.com");
        employee.setTelephone("123456789");
        employee.setAboutMe("About John");
        employee.setInterests("Interests of John");
        Set<Education> educationSet = new HashSet<>();
        educationSet.add(new Education(1L, "Education 1", employee));
        employee.setEducation(educationSet);
        Set<Experience> experienceSet = new HashSet<>();
        experienceSet.add(new Experience(1L, "Experience 1", employee));
        employee.setExperience(experienceSet);
        Set<Project> projectSet = new HashSet<>();
        projectSet.add(new Project(1L, "Project 1", employee));
        employee.setProjects(projectSet);
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(new Skill(1L, "Skill 1", employee));
        employee.setSkills(skillSet);

        // Act
        EmployeeDto employeeDto = employeeMapper.toDto(employee);

        // Assert
        assertNotNull(employeeDto);
        assertEquals(employee.getId(), employeeDto.getId());
        assertEquals(employee.getFirstName(), employeeDto.getFirstName());
        assertEquals(employee.getLastName(), employeeDto.getLastName());
        assertEquals(employee.getContactEmail(), employeeDto.getEmail());
        assertEquals(employee.getTelephone(), employeeDto.getTelephone());
        assertEquals(employee.getAboutMe(), employeeDto.getAboutMe());
        assertEquals(employee.getInterests(), employeeDto.getInterests());
        assertEquals(employee.getEducation().size(), employeeDto.getEducation().size());
        assertEquals(employee.getExperience().size(), employeeDto.getExperience().size());
        assertEquals(employee.getProjects().size(), employeeDto.getProjects().size());
        assertEquals(employee.getSkills().size(), employeeDto.getSkills().size());
    }

    @Test
    public void testToDtoWhenNullEmployeeThenDtoWithNullProperties() {
        // Arrange
        Employee employee = new Employee();

        // Act
        EmployeeDto employeeDto = employeeMapper.toDto(employee);

        // Assert
        assertNotNull(employeeDto);
        assertEquals(employee.getId(), employeeDto.getId());
        assertEquals(employee.getFirstName(), employeeDto.getFirstName());
        assertEquals(employee.getLastName(), employeeDto.getLastName());
        assertEquals(employee.getContactEmail(), employeeDto.getEmail());
        assertEquals(employee.getTelephone(), employeeDto.getTelephone());
        assertEquals(employee.getAboutMe(), employeeDto.getAboutMe());
        assertEquals(employee.getInterests(), employeeDto.getInterests());
    }

    @Test
    public void testToDtoWhenEmptyListsEmployeeThenDtoWithEmptyLists() {
        // Arrange
        Employee employee = new Employee();
        employee.setEducation(new HashSet<>());
        employee.setExperience(new HashSet<>());
        employee.setProjects(new HashSet<>());
        employee.setSkills(new HashSet<>());

        // Act
        EmployeeDto employeeDto = employeeMapper.toDto(employee);

        // Assert
        assertNotNull(employeeDto);
        assertEquals(employee.getId(), employeeDto.getId());
        assertEquals(employee.getFirstName(), employeeDto.getFirstName());
        assertEquals(employee.getLastName(), employeeDto.getLastName());
        assertEquals(employee.getContactEmail(), employeeDto.getEmail());
        assertEquals(employee.getTelephone(), employeeDto.getTelephone());
        assertEquals(employee.getAboutMe(), employeeDto.getAboutMe());
        assertEquals(employee.getInterests(), employeeDto.getInterests());
        assertEquals(0, employeeDto.getEducation().size());
        assertEquals(0, employeeDto.getExperience().size());
        assertEquals(0, employeeDto.getProjects().size());
        assertEquals(0, employeeDto.getSkills().size());
    }
}