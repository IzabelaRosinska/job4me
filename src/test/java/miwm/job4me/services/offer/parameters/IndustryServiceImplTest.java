package miwm.job4me.services.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.parameters.Industry;
import miwm.job4me.repositories.offer.IndustryRepository;
import miwm.job4me.validators.entity.offer.parameters.IndustryValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.mappers.offer.parameters.IndustryMapper;
import miwm.job4me.web.model.offer.IndustryDto;
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
class IndustryServiceImplTest {
    @Mock
    private IndustryRepository industryRepository;
    @Mock
    private IndustryMapper industryMapper;
    @Mock
    private IndustryValidator industryValidator;
    @Mock
    private IdValidator idValidator;
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private IndustryServiceImpl industryService;

    private final String ENTITY_NAME = "Industry";
    private final int MAX_DESCRIPTION_LENGTH = 100;

    private JobOffer jobOffer;
    private Industry industry1;
    private Industry industry2;
    private IndustryDto industryDto1;
    private IndustryDto industryDto2;

    @BeforeEach
    public void setUp() {
        industry1 = Industry
                .builder()
                .id(1L)
                .name("name1")
                .build();

        industryDto1 = new IndustryDto();
        industryDto1.setId(industry1.getId());
        industryDto1.setName(industry1.getName());

        industry2 = Industry
                .builder()
                .id(2L)
                .name("name2")
                .build();

        industryDto2 = new IndustryDto();
        industryDto2.setId(industry2.getId());
    }

    @Test
    @DisplayName("Test existsById - return true when Industry object exists")
    public void testExistsByIdExists() {
        when(industryRepository.existsById(industry1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(industry1.getId(), ENTITY_NAME);

        assertTrue(industryService.existsById(industry1.getId()));
    }

    @Test
    @DisplayName("Test existsById - return false when Industry object does not exist")
    public void testExistsByIdDoesNotExist() {
        when(industryRepository.existsById(industry1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(industry1.getId(), ENTITY_NAME);

        assertFalse(industryService.existsById(industry1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - do nothing when Industry object exists")
    public void testStrictExistsByIdExists() {
        when(industryRepository.existsById(industry1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(industry1.getId(), ENTITY_NAME);

        assertDoesNotThrow(() -> industryService.strictExistsById(industry1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - throw NoSuchElementFoundException when Industry object does not exist")
    public void testStrictExistsByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, industry1.getId());

        when(industryRepository.existsById(industry1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(industry1.getId(), ENTITY_NAME);

        try {
            industryService.strictExistsById(industry1.getId());
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
            industryService.strictExistsById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAll - return all existing Industry objects")
    public void testFindAll() {
        when(industryRepository.findAll()).thenReturn(List.of(industry1, industry2));
        when(industryMapper.toDto(industry1)).thenReturn(industryDto1);
        when(industryMapper.toDto(industry2)).thenReturn(industryDto2);

        Set<IndustryDto> result = industryService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(industryDto1));
        assertTrue(result.contains(industryDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set when there are no Industry objects")
    public void testFindAllEmpty() {
        when(industryRepository.findAll()).thenReturn(List.of());

        Set<IndustryDto> result = industryService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return Industry object when it exists")
    public void testFindById() {
        when(industryRepository.findById(industry1.getId())).thenReturn(java.util.Optional.of(industry1));
        when(industryMapper.toDto(industry1)).thenReturn(industryDto1);

        IndustryDto result = industryService.findById(industry1.getId());

        assertEquals(industryDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException when Industry object does not exist")
    public void testFindByIdNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound("Industry", industry1.getId());
        when(industryRepository.findById(industry1.getId())).thenReturn(java.util.Optional.empty());

        try {
            industryService.findById(industry1.getId());
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
            industryService.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return Industry object when it is valid")
    public void testSaveValid() {
        when(industryRepository.save(industry1)).thenReturn(industry1);
        when(industryMapper.toDto(industry1)).thenReturn(industryDto1);
        doNothing().when(industryValidator).validate(industry1);

        IndustryDto result = industryService.save(industry1);

        assertEquals(industryDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when Industry object is null")
    public void testSaveThrowExceptionNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(industryValidator).validate(null);

        try {
            industryService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when IndustryValidator fails")
    public void testSaveThrowExceptionIndustryValidatorFails() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH);

        industry1.setName("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(industryValidator).validate(industry1);

        try {
            industryService.save(industry1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Industry object exists")
    public void testDeleteIndustryExists() {
        when(industryRepository.existsById(industry1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(industry1.getId(), ENTITY_NAME);
        doNothing().when(industryRepository).delete(industry1);

        assertDoesNotThrow(() -> industryService.delete(industry1));
    }

    @Test
    @DisplayName("Test delete - Industry object does not exist")
    public void testDeleteIndustryDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, industry1.getId());
        when(industryRepository.existsById(industry1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(industry1.getId(), ENTITY_NAME);

        try {
            industryService.delete(industry1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Industry object is null")
    public void testDeleteIndustryNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            industryService.delete(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Industry id is null")
    public void testDeleteIndustryIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);
        industry1.setId(null);

        try {
            industryService.delete(industry1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - Industry object exists")
    public void testDeleteByIdIndustryExists() {
        when(industryRepository.existsById(industry1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(industry1.getId(), ENTITY_NAME);
        doNothing().when(industryRepository).deleteById(industry1.getId());

        assertDoesNotThrow(() -> industryService.deleteById(industry1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - Industry object does not exist")
    public void testDeleteByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, industry1.getId());

        when(industryRepository.existsById(industry1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(industry1.getId(), ENTITY_NAME);

        try {
            industryService.deleteById(industry1.getId());
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
            industryService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - update and return IndustryDto object when it is valid")
    public void testUpdateIndustryExists() {
        doNothing().when(idValidator).validateLongId(industry1.getId(), ENTITY_NAME);
        when(industryRepository.existsById(industry1.getId())).thenReturn(true);
        doNothing().when(industryValidator).validateDto(industryDto1);
        when(industryMapper.toEntity(industryDto1)).thenReturn(industry1);
        when(industryRepository.save(industry1)).thenReturn(industry1);
        when(industryMapper.toDto(industry1)).thenReturn(industryDto1);

        IndustryDto result = industryService.update(industryDto1.getId(), industryDto1);

        assertEquals(industryDto1, result);
    }

    @Test
    @DisplayName("Test update - throw NoSuchElementFoundException when IdValidator fails")
    public void testUpdateIndustryDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, industry1.getId());
        doThrow(new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, industry1.getId()))).when(idValidator).validateLongId(industry1.getId(), ENTITY_NAME);

        try {
            industryService.update(industry1.getId(), industryDto1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - throw InvalidArgumentException when IndustryDto object is null and IndustryValidator fails")
    public void testUpdateIndustryDtoNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        when(industryRepository.existsById(industry1.getId())).thenReturn(true);
        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(industryValidator).validateDto(null);

        try {
            industryService.update(industry1.getId(), null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findByName - return Industry object when it exists")
    public void testFindByName() {
        doNothing().when(stringFieldValidator).validateNotNullNotEmpty(industry1.getName(), ENTITY_NAME, "name");
        when(industryRepository.findByName(industry1.getName())).thenReturn(industry1);

        Industry result = industryService.findByName(industry1.getName());

        assertEquals(industry1, result);
    }

    @Test
    @DisplayName("Test findByName - throw NoSuchElementFoundException when Industry object does not exist")
    public void testFindByNameNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, "name", industry1.getName());

        doNothing().when(stringFieldValidator).validateNotNullNotEmpty(industry1.getName(), ENTITY_NAME, "name");
        when(industryRepository.findByName(industry1.getName())).thenReturn(null);

        try {
            industryService.findByName(industry1.getName());
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
            industryService.findByName(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test existsByName - return true when Industry object exists")
    public void testExistsByNameExists() {
        stringFieldValidator.validateNotNullNotEmpty(industry1.getName(), ENTITY_NAME, "name");
        when(industryRepository.existsByName(industry1.getName())).thenReturn(true);

        assertTrue(industryService.existsByName(industry1.getName()));
    }

    @Test
    @DisplayName("Test existsByName - return false when Industry object does not exist")
    public void testExistsByNameDoesNotExist() {
        stringFieldValidator.validateNotNullNotEmpty(industry1.getName(), ENTITY_NAME, "name");
        when(industryRepository.existsByName(industry1.getName())).thenReturn(false);

        assertFalse(industryService.existsByName(industry1.getName()));
    }

    @Test
    @DisplayName("Test existsByName - throw InvalidArgumentException when StringFieldValidator fails")
    public void testExistsByNameNull() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "name");

        doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateNotNullNotEmpty(null, ENTITY_NAME, "name");

        try {
            industryService.existsByName(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test strictExistsByName - do nothing when Industry object exists")
    public void testStrictExistsByNameExists() {
        when(industryRepository.existsByName(industry1.getName())).thenReturn(true);

        assertDoesNotThrow(() -> industryService.strictExistsByName(industry1.getName()));
    }

    @Test
    @DisplayName("Test strictExistsByName - throw NoSuchElementFoundException when Industry object does not exist")
    public void testStrictExistsByNameDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFoundByName(ENTITY_NAME, industry1.getName());
        when(industryRepository.existsByName(industry1.getName())).thenReturn(false);

        try {
            industryService.strictExistsByName(industry1.getName());
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
            industryService.strictExistsByName(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
