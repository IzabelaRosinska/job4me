package miwm.job4me.services.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Project;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.cv.ProjectRepository;
import miwm.job4me.validators.entity.cv.ProjectValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.cv.ProjectMapper;
import miwm.job4me.web.model.cv.ProjectDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private final int MAX_DESCRIPTION_LENGTH = 300;

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
    @DisplayName("Test existsById - return true when Project object exists")
    public void testExistsByIdExists() {
        when(projectRepository.existsById(project1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(project1.getId(), ENTITY_NAME);

        assertTrue(projectService.existsById(project1.getId()));
    }

    @Test
    @DisplayName("Test existsById - return false when Project object does not exist")
    public void testExistsByIdDoesNotExist() {
        when(projectRepository.existsById(project1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(project1.getId(), ENTITY_NAME);

        assertFalse(projectService.existsById(project1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - do nothing when Project object exists")
    public void testStrictExistsByIdExists() {
        when(projectRepository.existsById(project1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(project1.getId(), ENTITY_NAME);

        assertDoesNotThrow(() -> projectService.strictExistsById(project1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - throw NoSuchElementFoundException when Project object does not exist")
    public void testStrictExistsByIdDoesNotExist() {
        when(projectRepository.existsById(project1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(project1.getId(), ENTITY_NAME);

        try {
            projectService.strictExistsById(project1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, project1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test strictExistsById - throw InvalidArgumentException when id is null")
    public void testStrictExistsByIdNull() {
        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            projectService.strictExistsById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAll - return all existing Project objects")
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
    @DisplayName("Test findAll - return empty set when there are no Project objects")
    public void testFindAllEmpty() {
        when(projectRepository.findAll()).thenReturn(List.of());

        Set<ProjectDto> result = projectService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return Project object when it exists")
    public void testFindById() {
        when(projectRepository.findById(project1.getId())).thenReturn(java.util.Optional.of(project1));
        when(projectMapper.toDto(project1)).thenReturn(projectDto1);

        ProjectDto result = projectService.findById(project1.getId());

        assertEquals(projectDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException when Project object does not exist")
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
    @DisplayName("Test findById - throw InvalidArgumentException when id is null")
    public void testFindByIdNull() {
        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

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
        doNothing().when(projectValidator).validate(project1);

        ProjectDto result = projectService.save(project1);

        assertEquals(projectDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when Project object is null")
    public void testSaveThrowExceptionNull() {
        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(projectValidator).validate(null);

        try {
            projectService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when ProjectValidator fails")
    public void testSaveThrowExceptionProjectValidatorFails() {
        project1.setDescription("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(projectValidator).validate(project1);

        try {
            projectService.save(project1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Project object exists")
    public void testDeleteProjectExists() {
        when(projectRepository.existsById(project1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(project1.getId(), ENTITY_NAME);
        doNothing().when(projectRepository).delete(project1);

        assertDoesNotThrow(() -> projectService.delete(project1));
    }

    @Test
    @DisplayName("Test delete - Project object does not exist")
    public void testDeleteProjectDoesNotExist() {
        when(projectRepository.existsById(project1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(project1.getId(), ENTITY_NAME);

        try {
            projectService.delete(project1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, project1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Project object is null")
    public void testDeleteProjectNull() {
        try {
            projectService.delete(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Project id is null")
    public void testDeleteProjectIdNull() {
        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);
        project1.setId(null);

        try {
            projectService.delete(project1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - Project object exists")
    public void testDeleteByIdProjectExists() {
        when(projectRepository.existsById(project1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(project1.getId(), ENTITY_NAME);
        doNothing().when(projectRepository).deleteById(project1.getId());

        assertDoesNotThrow(() -> projectService.deleteById(project1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - Project object does not exist")
    public void testDeleteByIdDoesNotExist() {
        when(projectRepository.existsById(project1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(project1.getId(), ENTITY_NAME);

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
        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            projectService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - update and return ProjectDto object when it is valid")
    public void testUpdateProjectExists() {
        doNothing().when(idValidator).validateLongId(project1.getId(), ENTITY_NAME);
        when(projectRepository.existsById(project1.getId())).thenReturn(true);
        doNothing().when(projectValidator).validateDto(projectDto1);
        when(projectMapper.toEntity(projectDto1)).thenReturn(project1);
        when(projectRepository.save(project1)).thenReturn(project1);
        when(projectMapper.toDto(project1)).thenReturn(projectDto1);

        ProjectDto result = projectService.update(projectDto1);

        assertEquals(projectDto1, result);
    }

    @Test
    @DisplayName("Test update - throw NoSuchElementFoundException when IdValidator fails")
    public void testUpdateProjectDoesNotExist() {
        doThrow(new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, project1.getId()))).when(idValidator).validateLongId(project1.getId(), ENTITY_NAME);

        try {
            projectService.update(projectDto1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, project1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - throw InvalidArgumentException when ProjectDto object is null and ProjectValidator fails")
    public void testUpdateProjectDtoNull() {
        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(projectValidator).validateDto(null);

        try {
            projectService.update(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAllByEmployeeId - return all existing Project objects for given employee")
    public void testFindAllByEmployeeId() {
        when(projectRepository.findAllByEmployeeId(employee.getId())).thenReturn(List.of(project1, project2));
        when(projectMapper.toDto(project1)).thenReturn(projectDto1);
        when(projectMapper.toDto(project2)).thenReturn(projectDto2);

        List<ProjectDto> result = projectService.findAllByEmployeeId(employee.getId());

        assertEquals(2, result.size());
        assertTrue(result.contains(projectDto1));
        assertTrue(result.contains(projectDto2));
    }

    @Test
    @DisplayName("Test findAllByEmployeeId - return empty set when there are no Project objects for given employee")
    public void testFindAllByEmployeeIdEmpty() {
        when(projectRepository.findAllByEmployeeId(employee.getId())).thenReturn(List.of());

        List<ProjectDto> result = projectService.findAllByEmployeeId(employee.getId());

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findAllByEmployeeId - throw InvalidArgumentException when id is null")
    public void testFindAllByEmployeeIdNull() {
        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            projectService.findAllByEmployeeId(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test deleteAllByEmployeeId - delete all Project objects for given employee")
    public void testDeleteAllByEmployeeId() {
        doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        doNothing().when(projectRepository).deleteAllByEmployeeId(employee.getId());

        assertDoesNotThrow(() -> projectService.deleteAllByEmployeeId(employee.getId()));
    }

    @Test
    @DisplayName("Test deleteAllByEmployeeId - throw InvalidArgumentException when id is null")
    public void testDeleteAllByEmployeeIdNull() {
        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            projectService.deleteAllByEmployeeId(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }
}
