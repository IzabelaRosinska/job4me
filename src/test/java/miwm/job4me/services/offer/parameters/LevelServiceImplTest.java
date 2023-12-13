package miwm.job4me.services.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.parameters.Level;
import miwm.job4me.repositories.offer.LevelRepository;
import miwm.job4me.validators.entity.offer.parameters.LevelValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.mappers.offer.parameters.LevelMapper;
import miwm.job4me.web.model.offer.LevelDto;
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
class LevelServiceImplTest {
    @Mock
    private LevelRepository levelRepository;
    @Mock
    private LevelMapper levelMapper;
    @Mock
    private LevelValidator levelValidator;
    @Mock
    private IdValidator idValidator;
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private LevelServiceImpl levelService;

    private final String ENTITY_NAME = "Level";
    private final int MAX_DESCRIPTION_LENGTH = 20;

    private JobOffer jobOffer;
    private Level level1;
    private Level level2;
    private LevelDto levelDto1;
    private LevelDto levelDto2;

    @BeforeEach
    public void setUp() {
        level1 = Level
                .builder()
                .id(1L)
                .name("name1")
                .build();

        levelDto1 = new LevelDto();
        levelDto1.setId(level1.getId());
        levelDto1.setName(level1.getName());

        level2 = Level
                .builder()
                .id(2L)
                .name("name2")
                .build();

        levelDto2 = new LevelDto();
        levelDto2.setId(level2.getId());
    }

    @Test
    @DisplayName("Test existsById - return true when Level object exists")
    public void testExistsByIdExists() {
        when(levelRepository.existsById(level1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(level1.getId(), ENTITY_NAME);

        assertTrue(levelService.existsById(level1.getId()));
    }

    @Test
    @DisplayName("Test existsById - return false when Level object does not exist")
    public void testExistsByIdDoesNotExist() {
        when(levelRepository.existsById(level1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(level1.getId(), ENTITY_NAME);

        assertFalse(levelService.existsById(level1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - do nothing when Level object exists")
    public void testStrictExistsByIdExists() {
        when(levelRepository.existsById(level1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(level1.getId(), ENTITY_NAME);

        assertDoesNotThrow(() -> levelService.strictExistsById(level1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - throw NoSuchElementFoundException when Level object does not exist")
    public void testStrictExistsByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, level1.getId());

        when(levelRepository.existsById(level1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(level1.getId(), ENTITY_NAME);

        try {
            levelService.strictExistsById(level1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test strictExistsById - throw InvalidArgumentException when id is null")
    public void testStrictExistsByIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            levelService.strictExistsById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAll - return all existing Level objects")
    public void testFindAll() {
        when(levelRepository.findAll()).thenReturn(List.of(level1, level2));
        when(levelMapper.toDto(level1)).thenReturn(levelDto1);
        when(levelMapper.toDto(level2)).thenReturn(levelDto2);

        Set<LevelDto> result = levelService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(levelDto1));
        assertTrue(result.contains(levelDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set when there are no Level objects")
    public void testFindAllEmpty() {
        when(levelRepository.findAll()).thenReturn(List.of());

        Set<LevelDto> result = levelService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return Level object when it exists")
    public void testFindById() {
        when(levelRepository.findById(level1.getId())).thenReturn(java.util.Optional.of(level1));
        when(levelMapper.toDto(level1)).thenReturn(levelDto1);

        LevelDto result = levelService.findById(level1.getId());

        assertEquals(levelDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException when Level object does not exist")
    public void testFindByIdNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound("Level", level1.getId());
        when(levelRepository.findById(level1.getId())).thenReturn(java.util.Optional.empty());

        try {
            levelService.findById(level1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findById - throw InvalidArgumentException when id is null")
    public void testFindByIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            levelService.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return Level object when it is valid")
    public void testSaveValid() {
        when(levelRepository.save(level1)).thenReturn(level1);
        when(levelMapper.toDto(level1)).thenReturn(levelDto1);
        doNothing().when(levelValidator).validate(level1);

        LevelDto result = levelService.save(level1);

        assertEquals(levelDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when Level object is null")
    public void testSaveThrowExceptionNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(levelValidator).validate(null);

        try {
            levelService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when LevelValidator fails")
    public void testSaveThrowExceptionLevelValidatorFails() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH);

        level1.setName("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(levelValidator).validate(level1);

        try {
            levelService.save(level1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Level object exists")
    public void testDeleteLevelExists() {
        when(levelRepository.existsById(level1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(level1.getId(), ENTITY_NAME);
        doNothing().when(levelRepository).delete(level1);

        assertDoesNotThrow(() -> levelService.delete(level1));
    }

    @Test
    @DisplayName("Test delete - Level object does not exist")
    public void testDeleteLevelDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, level1.getId());
        when(levelRepository.existsById(level1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(level1.getId(), ENTITY_NAME);

        try {
            levelService.delete(level1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Level object is null")
    public void testDeleteLevelNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            levelService.delete(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Level id is null")
    public void testDeleteLevelIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);
        level1.setId(null);

        try {
            levelService.delete(level1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - Level object exists")
    public void testDeleteByIdLevelExists() {
        when(levelRepository.existsById(level1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(level1.getId(), ENTITY_NAME);
        doNothing().when(levelRepository).deleteById(level1.getId());

        assertDoesNotThrow(() -> levelService.deleteById(level1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - Level object does not exist")
    public void testDeleteByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, level1.getId());

        when(levelRepository.existsById(level1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(level1.getId(), ENTITY_NAME);

        try {
            levelService.deleteById(level1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - id is null")
    public void testDeleteByIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            levelService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - update and return LevelDto object when it is valid")
    public void testUpdateLevelExists() {
        doNothing().when(idValidator).validateLongId(level1.getId(), ENTITY_NAME);
        when(levelRepository.existsById(level1.getId())).thenReturn(true);
        doNothing().when(levelValidator).validateDto(levelDto1);
        when(levelMapper.toEntity(levelDto1)).thenReturn(level1);
        when(levelRepository.save(level1)).thenReturn(level1);
        when(levelMapper.toDto(level1)).thenReturn(levelDto1);

        LevelDto result = levelService.update(levelDto1.getId(), levelDto1);

        assertEquals(levelDto1, result);
    }

    @Test
    @DisplayName("Test update - throw NoSuchElementFoundException when IdValidator fails")
    public void testUpdateLevelDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, level1.getId());
        doThrow(new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, level1.getId()))).when(idValidator).validateLongId(level1.getId(), ENTITY_NAME);

        try {
            levelService.update(level1.getId(), levelDto1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - throw InvalidArgumentException when LevelDto object is null and LevelValidator fails")
    public void testUpdateLevelDtoNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        when(levelRepository.existsById(level1.getId())).thenReturn(true);
        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(levelValidator).validateDto(null);

        try {
            levelService.update(level1.getId(), null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findByName - return Level object when it exists")
    public void testFindByName() {
        doNothing().when(stringFieldValidator).validateNotNullNotEmpty(level1.getName(), ENTITY_NAME, "name");
        when(levelRepository.findByName(level1.getName())).thenReturn(level1);

        Level result = levelService.findByName(level1.getName());

        assertEquals(level1, result);
    }

    @Test
    @DisplayName("Test findByName - throw NoSuchElementFoundException when Level object does not exist")
    public void testFindByNameNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, "name", level1.getName());

        doNothing().when(stringFieldValidator).validateNotNullNotEmpty(level1.getName(), ENTITY_NAME, "name");
        when(levelRepository.findByName(level1.getName())).thenReturn(null);

        try {
            levelService.findByName(level1.getName());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findByName - throw InvalidArgumentException when StringFieldValidator fails")
    public void testFindByNameNull() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "name");

        doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateNotNullNotEmpty(null, ENTITY_NAME, "name");

        try {
            levelService.findByName(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test existsByName - return true when Level object exists")
    public void testExistsByNameExists() {
        stringFieldValidator.validateNotNullNotEmpty(level1.getName(), ENTITY_NAME, "name");
        when(levelRepository.existsByName(level1.getName())).thenReturn(true);

        assertTrue(levelService.existsByName(level1.getName()));
    }

    @Test
    @DisplayName("Test existsByName - return false when Level object does not exist")
    public void testExistsByNameDoesNotExist() {
        stringFieldValidator.validateNotNullNotEmpty(level1.getName(), ENTITY_NAME, "name");
        when(levelRepository.existsByName(level1.getName())).thenReturn(false);

        assertFalse(levelService.existsByName(level1.getName()));
    }

    @Test
    @DisplayName("Test existsByName - throw InvalidArgumentException when StringFieldValidator fails")
    public void testExistsByNameNull() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "name");

        doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateNotNullNotEmpty(null, ENTITY_NAME, "name");

        try {
            levelService.existsByName(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test strictExistsByName - do nothing when Level object exists")
    public void testStrictExistsByNameExists() {
        when(levelRepository.existsByName(level1.getName())).thenReturn(true);

        assertDoesNotThrow(() -> levelService.strictExistsByName(level1.getName()));
    }

    @Test
    @DisplayName("Test strictExistsByName - throw NoSuchElementFoundException when Level object does not exist")
    public void testStrictExistsByNameDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFoundByName(ENTITY_NAME, level1.getName());
        when(levelRepository.existsByName(level1.getName())).thenReturn(false);

        try {
            levelService.strictExistsByName(level1.getName());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test strictExistsByName - throw InvalidArgumentException when StringFieldValidator fails")
    public void testStrictExistsByNameNull() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "name");

        doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateNotNullNotEmpty(null, ENTITY_NAME, "name");

        try {
            levelService.strictExistsByName(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
