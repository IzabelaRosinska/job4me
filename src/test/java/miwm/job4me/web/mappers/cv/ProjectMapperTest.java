package miwm.job4me.web.mappers.cv;

import miwm.job4me.model.cv.Project;
import miwm.job4me.model.users.Employee;
import miwm.job4me.web.model.cv.ProjectDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProjectMapperTest {
    @InjectMocks
    private ProjectMapper projectMapper;

    private Project project;

    private ProjectDto projectDto;

    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        Employee employee = Employee.builder().id(ID).build();

        project = Project.builder()
                .id(ID)
                .description("description")
                .employee(employee)
                .build();

        projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setDescription(project.getDescription());
        projectDto.setEmployeeId(project.getEmployee().getId());
    }

    @Test
    @DisplayName("test toDto - should return dto with the same values as entity")
    void toDto() {
        ProjectDto projectDtoResult = projectMapper.toDto(project);

        assertEquals(projectDto.getId(), projectDtoResult.getId());
        assertEquals(projectDto.getDescription(), projectDtoResult.getDescription());
        assertEquals(projectDto.getEmployeeId(), projectDtoResult.getEmployeeId());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toEntity() {
        Project projectResult = projectMapper.toEntity(projectDto);

        assertEquals(project.getId(), projectResult.getId());
        assertEquals(project.getDescription(), projectResult.getDescription());
    }
}
