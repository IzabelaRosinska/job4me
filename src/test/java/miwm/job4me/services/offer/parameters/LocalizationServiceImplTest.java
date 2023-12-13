package miwm.job4me.services.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.parameters.Localization;
import miwm.job4me.repositories.offer.LocalizationRepository;
import miwm.job4me.validators.entity.offer.parameters.LocalizationValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.mappers.offer.parameters.LocalizationMapper;
import miwm.job4me.web.model.offer.LocalizationDto;
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
class LocalizationServiceImplTest {
    @Mock
    private LocalizationRepository localizationRepository;
    @Mock
    private LocalizationMapper localizationMapper;
    @Mock
    private LocalizationValidator localizationValidator;
    @Mock
    private IdValidator idValidator;
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private LocalizationServiceImpl localizationService;

    private final String ENTITY_NAME = "Localization";
    private final int MAX_DESCRIPTION_LENGTH = 50;

    private JobOffer jobOffer;
    private Localization localization1;
    private Localization localization2;
    private LocalizationDto localizationDto1;
    private LocalizationDto localizationDto2;

    @BeforeEach
    public void setUp() {
        localization1 = Localization
                .builder()
                .id(1L)
                .city("City1")
                .build();

        localizationDto1 = new LocalizationDto();
        localizationDto1.setId(localization1.getId());
        localizationDto1.setCity(localization1.getCity());

        localization2 = Localization
                .builder()
                .id(2L)
                .city("City2")
                .build();

        localizationDto2 = new LocalizationDto();
        localizationDto2.setId(localization2.getId());
    }

    @Test
    @DisplayName("Test existsById - return true when Localization object exists")
    public void testExistsByIdExists() {
        when(localizationRepository.existsById(localization1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(localization1.getId(), ENTITY_NAME);

        assertTrue(localizationService.existsById(localization1.getId()));
    }

    @Test
    @DisplayName("Test existsById - return false when Localization object does not exist")
    public void testExistsByIdDoesNotExist() {
        when(localizationRepository.existsById(localization1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(localization1.getId(), ENTITY_NAME);

        assertFalse(localizationService.existsById(localization1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - do nothing when Localization object exists")
    public void testStrictExistsByIdExists() {
        when(localizationRepository.existsById(localization1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(localization1.getId(), ENTITY_NAME);

        assertDoesNotThrow(() -> localizationService.strictExistsById(localization1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - throw NoSuchElementFoundException when Localization object does not exist")
    public void testStrictExistsByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, localization1.getId());

        when(localizationRepository.existsById(localization1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(localization1.getId(), ENTITY_NAME);

        try {
            localizationService.strictExistsById(localization1.getId());
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
            localizationService.strictExistsById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAll - return all existing Localization objects")
    public void testFindAll() {
        when(localizationRepository.findAll()).thenReturn(List.of(localization1, localization2));
        when(localizationMapper.toDto(localization1)).thenReturn(localizationDto1);
        when(localizationMapper.toDto(localization2)).thenReturn(localizationDto2);

        Set<LocalizationDto> result = localizationService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(localizationDto1));
        assertTrue(result.contains(localizationDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set when there are no Localization objects")
    public void testFindAllEmpty() {
        when(localizationRepository.findAll()).thenReturn(List.of());

        Set<LocalizationDto> result = localizationService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return Localization object when it exists")
    public void testFindById() {
        when(localizationRepository.findById(localization1.getId())).thenReturn(java.util.Optional.of(localization1));
        when(localizationMapper.toDto(localization1)).thenReturn(localizationDto1);

        LocalizationDto result = localizationService.findById(localization1.getId());

        assertEquals(localizationDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException when Localization object does not exist")
    public void testFindByIdNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound("Localization", localization1.getId());
        when(localizationRepository.findById(localization1.getId())).thenReturn(java.util.Optional.empty());

        try {
            localizationService.findById(localization1.getId());
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
            localizationService.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return Localization object when it is valid")
    public void testSaveValid() {
        when(localizationRepository.save(localization1)).thenReturn(localization1);
        when(localizationMapper.toDto(localization1)).thenReturn(localizationDto1);
        doNothing().when(localizationValidator).validate(localization1);

        LocalizationDto result = localizationService.save(localization1);

        assertEquals(localizationDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when Localization object is null")
    public void testSaveThrowExceptionNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(localizationValidator).validate(null);

        try {
            localizationService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when LocalizationValidator fails")
    public void testSaveThrowExceptionLocalizationValidatorFails() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH);

        localization1.setCity("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(localizationValidator).validate(localization1);

        try {
            localizationService.save(localization1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Localization object exists")
    public void testDeleteLocalizationExists() {
        when(localizationRepository.existsById(localization1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(localization1.getId(), ENTITY_NAME);
        doNothing().when(localizationRepository).delete(localization1);

        assertDoesNotThrow(() -> localizationService.delete(localization1));
    }

    @Test
    @DisplayName("Test delete - Localization object does not exist")
    public void testDeleteLocalizationDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, localization1.getId());
        when(localizationRepository.existsById(localization1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(localization1.getId(), ENTITY_NAME);

        try {
            localizationService.delete(localization1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Localization object is null")
    public void testDeleteLocalizationNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            localizationService.delete(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Localization id is null")
    public void testDeleteLocalizationIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);
        localization1.setId(null);

        try {
            localizationService.delete(localization1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - Localization object exists")
    public void testDeleteByIdLocalizationExists() {
        when(localizationRepository.existsById(localization1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(localization1.getId(), ENTITY_NAME);
        doNothing().when(localizationRepository).deleteById(localization1.getId());

        assertDoesNotThrow(() -> localizationService.deleteById(localization1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - Localization object does not exist")
    public void testDeleteByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, localization1.getId());

        when(localizationRepository.existsById(localization1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(localization1.getId(), ENTITY_NAME);

        try {
            localizationService.deleteById(localization1.getId());
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
            localizationService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - update and return LocalizationDto object when it is valid")
    public void testUpdateLocalizationExists() {
        doNothing().when(idValidator).validateLongId(localization1.getId(), ENTITY_NAME);
        when(localizationRepository.existsById(localization1.getId())).thenReturn(true);
        doNothing().when(localizationValidator).validateDto(localizationDto1);
        when(localizationMapper.toEntity(localizationDto1)).thenReturn(localization1);
        when(localizationRepository.save(localization1)).thenReturn(localization1);
        when(localizationMapper.toDto(localization1)).thenReturn(localizationDto1);

        LocalizationDto result = localizationService.update(localizationDto1.getId(), localizationDto1);

        assertEquals(localizationDto1, result);
    }

    @Test
    @DisplayName("Test update - throw NoSuchElementFoundException when IdValidator fails")
    public void testUpdateLocalizationDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, localization1.getId());
        doThrow(new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, localization1.getId()))).when(idValidator).validateLongId(localization1.getId(), ENTITY_NAME);

        try {
            localizationService.update(localization1.getId(), localizationDto1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - throw InvalidArgumentException when LocalizationDto object is null and LocalizationValidator fails")
    public void testUpdateLocalizationDtoNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        when(localizationRepository.existsById(localization1.getId())).thenReturn(true);
        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(localizationValidator).validateDto(null);

        try {
            localizationService.update(localization1.getId(), null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findByCity - return Localization object when it exists")
    public void testFindByCity() {
        doNothing().when(stringFieldValidator).validateNotNullNotEmpty(localization1.getCity(), ENTITY_NAME, "city");
        when(localizationRepository.findByCity(localization1.getCity())).thenReturn(localization1);

        Localization result = localizationService.findByCity(localization1.getCity());

        assertEquals(localization1, result);
    }

    @Test
    @DisplayName("Test findByCity - throw NoSuchElementFoundException when Localization object does not exist")
    public void testFindByCityNullId() {
        String expectedMessage = ExceptionMessages.elementNotFoundByName(ENTITY_NAME, localization1.getCity());

        doNothing().when(stringFieldValidator).validateNotNullNotEmpty(localization1.getCity(), ENTITY_NAME, "city");
        when(localizationRepository.findByCity(localization1.getCity())).thenReturn(null);

        try {
            localizationService.findByCity(localization1.getCity());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findByCity - throw InvalidArgumentException when StringFieldValidator fails")
    public void testFindByCityNull() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "city");

        doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateNotNullNotEmpty(null, ENTITY_NAME, "city");

        try {
            localizationService.findByCity(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test existsByCity - return true when Localization object exists")
    public void testExistsByCityExists() {
        stringFieldValidator.validateNotNullNotEmpty(localization1.getCity(), ENTITY_NAME, "City");
        when(localizationRepository.existsByCity(localization1.getCity())).thenReturn(true);

        assertTrue(localizationService.existsByCity(localization1.getCity()));
    }

    @Test
    @DisplayName("Test existsByCity - return false when Localization object does not exist")
    public void testExistsByCityDoesNotExist() {
        stringFieldValidator.validateNotNullNotEmpty(localization1.getCity(), ENTITY_NAME, "City");
        when(localizationRepository.existsByCity(localization1.getCity())).thenReturn(false);

        assertFalse(localizationService.existsByCity(localization1.getCity()));
    }

    @Test
    @DisplayName("Test existsByCity - throw InvalidArgumentException when StringFieldValidator fails")
    public void testExistsByCityNull() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "city");

        doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateNotNullNotEmpty(null, ENTITY_NAME, "city");

        try {
            localizationService.existsByCity(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test strictExistsByCity - do nothing when Localization object exists")
    public void testStrictExistsByCityExists() {
        when(localizationRepository.existsByCity(localization1.getCity())).thenReturn(true);

        assertDoesNotThrow(() -> localizationService.strictExistsByCity(localization1.getCity()));
    }

    @Test
    @DisplayName("Test strictExistsByCity - throw NoSuchElementFoundException when Localization object does not exist")
    public void testStrictExistsByCityDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFoundByName(ENTITY_NAME, localization1.getCity());
        when(localizationRepository.existsByCity(localization1.getCity())).thenReturn(false);

        try {
            localizationService.strictExistsByCity(localization1.getCity());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test strictExistsByCity - throw InvalidArgumentException when StringFieldValidator fails")
    public void testStrictExistsByCityNull() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "city");

        doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateNotNullNotEmpty(null, ENTITY_NAME, "city");

        try {
            localizationService.strictExistsByCity(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
