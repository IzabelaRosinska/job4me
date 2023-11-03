package miwm.job4me.services.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.cv.ExperienceRepository;
import miwm.job4me.validators.entity.cv.ExperienceValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.cv.ExperienceMapper;
import miwm.job4me.web.model.cv.ExperienceDto;
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
class ExperienceServiceImplTest {
    @Mock
    private ExperienceRepository experienceRepository;
    @Mock
    private ExperienceMapper experienceMapper;
    @Mock
    private ExperienceValidator experienceValidator;
    @Mock
    private IdValidator idValidator;
    @InjectMocks
    private ExperienceServiceImpl experienceService;

    private final String ENTITY_NAME = "Experience";
    private final int MAX_DESCRIPTION_LENGTH = 255;

    private Employee employee;
    private Experience experience1;
    private Experience experience2;
    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;


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

        experience1 = Experience
                .builder()
                .id(1L)
                .description("description1")
                .employee(null)
                .build();

        experienceDto1 = new ExperienceDto();
        experienceDto1.setId(experience1.getId());
        experienceDto1.setDescription(experience1.getDescription());
        experienceDto1.setEmployeeId(employee.getId());

        experience2 = Experience
                .builder()
                .id(2L)
                .description("description2")
                .employee(employee)
                .build();

        experienceDto2 = new ExperienceDto();
        experienceDto2.setId(experience2.getId());
        experienceDto2.setDescription(experience2.getDescription());
        experienceDto2.setEmployeeId(employee.getId());
    }

    @Test
    @DisplayName("Test existsById - return true when Experience object exists")
    public void testExistsByIdExists() {
        when(experienceRepository.existsById(experience1.getId())).thenReturn(true);
        Mockito.doNothing().when(idValidator).validateLongId(experience1.getId(), ENTITY_NAME);

        assertTrue(experienceService.existsById(experience1.getId()));
    }

    @Test
    @DisplayName("Test existsById - return false when Experience object does not exist")
    public void testExistsByIdDoesNotExist() {
        when(experienceRepository.existsById(experience1.getId())).thenReturn(false);
        Mockito.doNothing().when(idValidator).validateLongId(experience1.getId(), ENTITY_NAME);

        assertFalse(experienceService.existsById(experience1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - do nothing when Experience object exists")
    public void testStrictExistsByIdExists() {
        when(experienceRepository.existsById(experience1.getId())).thenReturn(true);
        Mockito.doNothing().when(idValidator).validateLongId(experience1.getId(), ENTITY_NAME);

        assertDoesNotThrow(() -> experienceService.strictExistsById(experience1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - throw NoSuchElementFoundException when Experience object does not exist")
    public void testStrictExistsByIdDoesNotExist() {
        when(experienceRepository.existsById(experience1.getId())).thenReturn(false);
        Mockito.doNothing().when(idValidator).validateLongId(experience1.getId(), ENTITY_NAME);

        try {
            experienceService.strictExistsById(experience1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, experience1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test strictExistsById - throw InvalidArgumentException when id is null")
    public void testStrictExistsByIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            experienceService.strictExistsById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAll - return all existing Experience objects")
    public void testFindAll() {
        when(experienceRepository.findAll()).thenReturn(List.of(experience1, experience2));
        when(experienceMapper.toDto(experience1)).thenReturn(experienceDto1);
        when(experienceMapper.toDto(experience2)).thenReturn(experienceDto2);

        Set<ExperienceDto> result = experienceService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(experienceDto1));
        assertTrue(result.contains(experienceDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set when there are no Experience objects")
    public void testFindAllEmpty() {
        when(experienceRepository.findAll()).thenReturn(List.of());

        Set<ExperienceDto> result = experienceService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return Experience object when it exists")
    public void testFindById() {
        when(experienceRepository.findById(experience1.getId())).thenReturn(java.util.Optional.of(experience1));
        when(experienceMapper.toDto(experience1)).thenReturn(experienceDto1);

        ExperienceDto result = experienceService.findById(experience1.getId());

        assertEquals(experienceDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException when Experience object does not exist")
    public void testFindByIdNullId() {
        when(experienceRepository.findById(experience1.getId())).thenReturn(java.util.Optional.empty());

        try {
            experienceService.findById(experience1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound("Experience", experience1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findById - throw InvalidArgumentException when id is null")
    public void testFindByIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            experienceService.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return Experience object when it is valid")
    public void testSaveValid() {
        when(experienceRepository.save(experience1)).thenReturn(experience1);
        when(experienceMapper.toDto(experience1)).thenReturn(experienceDto1);
        Mockito.doNothing().when(experienceValidator).validate(experience1);

        ExperienceDto result = experienceService.save(experience1);

        assertEquals(experienceDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when Experience object is null")
    public void testSaveThrowExceptionNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(experienceValidator).validate(null);

        try {
            experienceService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when ExperienceValidator fails")
    public void testSaveThrowExceptionExperienceValidatorFails() {
        experience1.setDescription("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(experienceValidator).validate(experience1);

        try {
            experienceService.save(experience1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Experience object exists")
    public void testDeleteExperienceExists() {
        when(experienceRepository.existsById(experience1.getId())).thenReturn(true);
        Mockito.doNothing().when(idValidator).validateLongId(experience1.getId(), ENTITY_NAME);
        Mockito.doNothing().when(experienceRepository).delete(experience1);

        assertDoesNotThrow(() -> experienceService.delete(experience1));
    }

    @Test
    @DisplayName("Test delete - Experience object does not exist")
    public void testDeleteExperienceDoesNotExist() {
        when(experienceRepository.existsById(experience1.getId())).thenReturn(false);
        Mockito.doNothing().when(idValidator).validateLongId(experience1.getId(), ENTITY_NAME);

        try {
            experienceService.delete(experience1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, experience1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Experience object is null")
    public void testDeleteExperienceNull() {
        try {
            experienceService.delete(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Experience id is null")
    public void testDeleteExperienceIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);
        experience1.setId(null);

        try {
            experienceService.delete(experience1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - Experience object exists")
    public void testDeleteByIdExperienceExists() {
        when(experienceRepository.existsById(experience1.getId())).thenReturn(true);
        Mockito.doNothing().when(idValidator).validateLongId(experience1.getId(), ENTITY_NAME);
        Mockito.doNothing().when(experienceRepository).deleteById(experience1.getId());

        assertDoesNotThrow(() -> experienceService.deleteById(experience1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - Experience object does not exist")
    public void testDeleteByIdDoesNotExist() {
        when(experienceRepository.existsById(experience1.getId())).thenReturn(false);
        Mockito.doNothing().when(idValidator).validateLongId(experience1.getId(), ENTITY_NAME);

        try {
            experienceService.deleteById(experience1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, experience1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - id is null")
    public void testDeleteByIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            experienceService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - update and return ExperienceDto object when it is valid")
    public void testUpdateExperienceExists() {
        Mockito.doNothing().when(idValidator).validateLongId(experience1.getId(), ENTITY_NAME);
        when(experienceRepository.existsById(experience1.getId())).thenReturn(true);
        Mockito.doNothing().when(experienceValidator).validateDto(experienceDto1);
        when(experienceMapper.toEntity(experienceDto1)).thenReturn(experience1);
        when(experienceRepository.save(experience1)).thenReturn(experience1);
        when(experienceMapper.toDto(experience1)).thenReturn(experienceDto1);

        ExperienceDto result = experienceService.update(experienceDto1);

        assertEquals(experienceDto1, result);
    }

    @Test
    @DisplayName("Test update - throw NoSuchElementFoundException when IdValidator fails")
    public void testUpdateExperienceDoesNotExist() {
        Mockito.doThrow(new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, experience1.getId()))).when(idValidator).validateLongId(experience1.getId(), ENTITY_NAME);

        try {
            experienceService.update(experienceDto1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, experience1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - throw InvalidArgumentException when ExperienceDto object is null and ExperienceValidator fails")
    public void testUpdateExperienceDtoNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(experienceValidator).validateDto(null);

        try {
            experienceService.update(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAllByEmployeeId - return all existing Experience objects for given employee")
    public void testFindAllByEmployeeId() {
        when(experienceRepository.findAllByEmployeeId(employee.getId())).thenReturn(List.of(experience1, experience2));
        when(experienceMapper.toDto(experience1)).thenReturn(experienceDto1);
        when(experienceMapper.toDto(experience2)).thenReturn(experienceDto2);

        Set<ExperienceDto> result = experienceService.findAllByEmployeeId(employee.getId());

        assertEquals(2, result.size());
        assertTrue(result.contains(experienceDto1));
        assertTrue(result.contains(experienceDto2));
    }

    @Test
    @DisplayName("Test findAllByEmployeeId - return empty set when there are no Experience objects for given employee")
    public void testFindAllByEmployeeIdEmpty() {
        when(experienceRepository.findAllByEmployeeId(employee.getId())).thenReturn(List.of());

        Set<ExperienceDto> result = experienceService.findAllByEmployeeId(employee.getId());

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findAllByEmployeeId - throw InvalidArgumentException when id is null")
    public void testFindAllByEmployeeIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            experienceService.findAllByEmployeeId(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test deleteAllByEmployeeId - delete all Experience objects for given employee")
    public void testDeleteAllByEmployeeId() {
        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.doNothing().when(experienceRepository).deleteAllByEmployeeId(employee.getId());

        assertDoesNotThrow(() -> experienceService.deleteAllByEmployeeId(employee.getId()));
    }

    @Test
    @DisplayName("Test deleteAllByEmployeeId - throw InvalidArgumentException when id is null")
    public void testDeleteAllByEmployeeIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            experienceService.deleteAllByEmployeeId(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }
}
