package miwm.job4me.services.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Project;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.cv.ProjectRepository;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.validators.entity.cv.ProjectValidator;
import miwm.job4me.web.mappers.cv.ProjectMapper;
import miwm.job4me.web.model.cv.ProjectDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMapper projectMapper;
    @Mock
    private ProjectValidator projectValidator;
    @Mock
    private IdValidator idValidator;
    @InjectMocks
    private ProjectServiceImpl projectService;

    private final String ENTITY_NAME = "Project";
    private final int MAX_DESCRIPTION_LENGTH = 500;

    private Employee employee;
    private Project project1;
    private Project project2;
    private ProjectDto projectDto1;
    private ProjectDto projectDto2;

    @BeforeEach
    public void setUp() {
        employee = Employee
                .builder()
                .id(1L)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("unitTest@gmail.com")
                .password("password")
                .build();

        project1 = Project
                .builder()
                .id(1L)
                .description("description1")
                .employee(null)
                .build();

        projectDto1 = new ProjectDto();
        projectDto1.setId(project1.getId());
        projectDto1.setDescription(project1.getDescription());
        projectDto1.setEmployeeId(employee.getId());

        project2 = Project
                .builder()
                .id(2L)
                .description("description2")
                .employee(employee)
                .build();

        projectDto2 = new ProjectDto();
        projectDto2.setId(project2.getId());
        projectDto2.setDescription(project2.getDescription());
        projectDto2.setEmployeeId(employee.getId());
    }

    @Test
    @DisplayName("Test findAll - return all Project objects")
    public void testFindAll() {
        when(projectRepository.findAll()).thenReturn(List.of(project1, project2));
        when(projectMapper.toDto(project1)).thenReturn(projectDto1);
        when(projectMapper.toDto(project2)).thenReturn(projectDto2);

        Set<ProjectDto> result = projectService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(projectDto1));
        assertTrue(result.contains(projectDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set")
    public void testFindAllEmpty() {
        when(projectRepository.findAll()).thenReturn(List.of());

        Set<ProjectDto> result = projectService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return Project object")
    public void testFindById() {
        when(projectRepository.findById(project1.getId())).thenReturn(java.util.Optional.of(project1));
        when(projectMapper.toDto(project1)).thenReturn(projectDto1);

        ProjectDto result = projectService.findById(project1.getId());

        assertEquals(projectDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException")
    public void testFindByIdNullId() {
        when(projectRepository.findById(project1.getId())).thenReturn(java.util.Optional.empty());

        try {
            projectService.findById(project1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound("Project", project1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findById - id is null")
    public void testFindByIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            projectService.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return Project object when it is valid")
    public void testSaveValid() {
        when(projectRepository.save(project1)).thenReturn(project1);
        when(projectMapper.toDto(project1)).thenReturn(projectDto1);
        Mockito.doNothing().when(projectValidator).validate(project1);

        ProjectDto result = projectService.save(project1);

        assertEquals(projectDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when Project object is null")
    public void testSaveThrowExceptionNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(projectValidator).validate(null);

        try {
            projectService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException, description too long")
    public void testSaveThrowExceptionDescriptionTooLong() {
        project1.setDescription("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(projectValidator).validate(project1);

        try {
            projectService.save(project1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - Project object exists")
    public void testDeleteByIdExists() {
        when(projectRepository.findById(project1.getId())).thenReturn(java.util.Optional.of(project1));
        Mockito.doNothing().when(idValidator).validateLongId(project1.getId(), ENTITY_NAME);
        Mockito.doNothing().when(projectRepository).deleteById(project1.getId());

        assertDoesNotThrow(() -> projectService.deleteById(project1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - Project object does not exist")
    public void testDeleteByIdDoesNotExist() {
        when(projectRepository.findById(project1.getId())).thenReturn(java.util.Optional.empty());
        Mockito.doNothing().when(idValidator).validateLongId(project1.getId(), ENTITY_NAME);

        try {
            projectService.deleteById(project1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, project1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - id is null")
    public void testDeleteByIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            projectService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }
}