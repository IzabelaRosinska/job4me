package miwm.job4me.services.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.parameters.EmploymentForm;
import miwm.job4me.repositories.offer.EmploymentFormRepository;
import miwm.job4me.validators.entity.offer.parameters.EmploymentFormValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.mappers.offer.parameters.EmploymentFormMapper;
import miwm.job4me.web.model.offer.EmploymentFormDto;
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
class EmploymentFormServiceImplTest {
    @Mock
    private EmploymentFormRepository employmentFormRepository;
    @Mock
    private EmploymentFormMapper employmentFormMapper;
    @Mock
    private EmploymentFormValidator employmentFormValidator;
    @Mock
    private IdValidator idValidator;
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private EmploymentFormServiceImpl employmentFormService;

    private final String ENTITY_NAME = "EmploymentForm";
    private final int MAX_DESCRIPTION_LENGTH = 25;

    private JobOffer jobOffer;
    private EmploymentForm employmentForm;
    private EmploymentForm employmentForm2;
    private EmploymentFormDto employmentFormDto1;
    private EmploymentFormDto employmentFormDto2;

    @BeforeEach
    public void setUp() {
        employmentForm = EmploymentForm
                .builder()
                .id(1L)
                .name("name1")
                .build();

        employmentFormDto1 = new EmploymentFormDto();
        employmentFormDto1.setId(employmentForm.getId());
        employmentFormDto1.setName(employmentForm.getName());

        employmentForm2 = EmploymentForm
                .builder()
                .id(2L)
                .name("name2")
                .build();

        employmentFormDto2 = new EmploymentFormDto();
        employmentFormDto2.setId(employmentForm2.getId());
    }

    @Test
    @DisplayName("Test existsById - return true when EmploymentForm object exists")
    public void testExistsByIdExists() {
        when(employmentFormRepository.existsById(employmentForm.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(employmentForm.getId(), ENTITY_NAME);

        assertTrue(employmentFormService.existsById(employmentForm.getId()));
    }

    @Test
    @DisplayName("Test existsById - return false when EmploymentForm object does not exist")
    public void testExistsByIdDoesNotExist() {
        when(employmentFormRepository.existsById(employmentForm.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(employmentForm.getId(), ENTITY_NAME);

        assertFalse(employmentFormService.existsById(employmentForm.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - do nothing when EmploymentForm object exists")
    public void testStrictExistsByIdExists() {
        when(employmentFormRepository.existsById(employmentForm.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(employmentForm.getId(), ENTITY_NAME);

        assertDoesNotThrow(() -> employmentFormService.strictExistsById(employmentForm.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - throw NoSuchElementFoundException when EmploymentForm object does not exist")
    public void testStrictExistsByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, employmentForm.getId());

        when(employmentFormRepository.existsById(employmentForm.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(employmentForm.getId(), ENTITY_NAME);

        try {
            employmentFormService.strictExistsById(employmentForm.getId());
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
            employmentFormService.strictExistsById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAll - return all existing EmploymentForm objects")
    public void testFindAll() {
        when(employmentFormRepository.findAll()).thenReturn(List.of(employmentForm, employmentForm2));
        when(employmentFormMapper.toDto(employmentForm)).thenReturn(employmentFormDto1);
        when(employmentFormMapper.toDto(employmentForm2)).thenReturn(employmentFormDto2);

        Set<EmploymentFormDto> result = employmentFormService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(employmentFormDto1));
        assertTrue(result.contains(employmentFormDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set when there are no EmploymentForm objects")
    public void testFindAllEmpty() {
        when(employmentFormRepository.findAll()).thenReturn(List.of());

        Set<EmploymentFormDto> result = employmentFormService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return EmploymentForm object when it exists")
    public void testFindById() {
        when(employmentFormRepository.findById(employmentForm.getId())).thenReturn(java.util.Optional.of(employmentForm));
        when(employmentFormMapper.toDto(employmentForm)).thenReturn(employmentFormDto1);

        EmploymentFormDto result = employmentFormService.findById(employmentForm.getId());

        assertEquals(employmentFormDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException when EmploymentForm object does not exist")
    public void testFindByIdNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound("EmploymentForm", employmentForm.getId());
        when(employmentFormRepository.findById(employmentForm.getId())).thenReturn(java.util.Optional.empty());

        try {
            employmentFormService.findById(employmentForm.getId());
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
            employmentFormService.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return EmploymentForm object when it is valid")
    public void testSaveValid() {
        when(employmentFormRepository.save(employmentForm)).thenReturn(employmentForm);
        when(employmentFormMapper.toDto(employmentForm)).thenReturn(employmentFormDto1);
        doNothing().when(employmentFormValidator).validate(employmentForm);

        EmploymentFormDto result = employmentFormService.save(employmentForm);

        assertEquals(employmentFormDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when EmploymentForm object is null")
    public void testSaveThrowExceptionNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(employmentFormValidator).validate(null);

        try {
            employmentFormService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when EmploymentFormValidator fails")
    public void testSaveThrowExceptionEmploymentFormValidatorFails() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH);

        employmentForm.setName("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(employmentFormValidator).validate(employmentForm);

        try {
            employmentFormService.save(employmentForm);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - EmploymentForm object exists")
    public void testDeleteEmploymentFormExists() {
        when(employmentFormRepository.existsById(employmentForm.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(employmentForm.getId(), ENTITY_NAME);
        doNothing().when(employmentFormRepository).delete(employmentForm);

        assertDoesNotThrow(() -> employmentFormService.delete(employmentForm));
    }

    @Test
    @DisplayName("Test delete - EmploymentForm object does not exist")
    public void testDeleteEmploymentFormDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, employmentForm.getId());
        when(employmentFormRepository.existsById(employmentForm.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(employmentForm.getId(), ENTITY_NAME);

        try {
            employmentFormService.delete(employmentForm);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - EmploymentForm object is null")
    public void testDeleteEmploymentFormNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            employmentFormService.delete(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - EmploymentForm id is null")
    public void testDeleteEmploymentFormIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);
        employmentForm.setId(null);

        try {
            employmentFormService.delete(employmentForm);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - EmploymentForm object exists")
    public void testDeleteByIdEmploymentFormExists() {
        when(employmentFormRepository.existsById(employmentForm.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(employmentForm.getId(), ENTITY_NAME);
        doNothing().when(employmentFormRepository).deleteById(employmentForm.getId());

        assertDoesNotThrow(() -> employmentFormService.deleteById(employmentForm.getId()));
    }

    @Test
    @DisplayName("Test delete by id - EmploymentForm object does not exist")
    public void testDeleteByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, employmentForm.getId());

        when(employmentFormRepository.existsById(employmentForm.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(employmentForm.getId(), ENTITY_NAME);

        try {
            employmentFormService.deleteById(employmentForm.getId());
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
            employmentFormService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - update and return EmploymentFormDto object when it is valid")
    public void testUpdateEmploymentFormExists() {
        doNothing().when(idValidator).validateLongId(employmentForm.getId(), ENTITY_NAME);
        when(employmentFormRepository.existsById(employmentForm.getId())).thenReturn(true);
        doNothing().when(employmentFormValidator).validateDto(employmentFormDto1);
        when(employmentFormMapper.toEntity(employmentFormDto1)).thenReturn(employmentForm);
        when(employmentFormRepository.save(employmentForm)).thenReturn(employmentForm);
        when(employmentFormMapper.toDto(employmentForm)).thenReturn(employmentFormDto1);

        EmploymentFormDto result = employmentFormService.update(employmentFormDto1.getId(), employmentFormDto1);

        assertEquals(employmentFormDto1, result);
    }

    @Test
    @DisplayName("Test update - throw NoSuchElementFoundException when IdValidator fails")
    public void testUpdateEmploymentFormDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, employmentForm.getId());
        doThrow(new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, employmentForm.getId()))).when(idValidator).validateLongId(employmentForm.getId(), ENTITY_NAME);

        try {
            employmentFormService.update(employmentForm.getId(), employmentFormDto1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - throw InvalidArgumentException when EmploymentFormDto object is null and EmploymentFormValidator fails")
    public void testUpdateEmploymentFormDtoNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        when(employmentFormRepository.existsById(employmentForm.getId())).thenReturn(true);
        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(employmentFormValidator).validateDto(null);

        try {
            employmentFormService.update(employmentForm.getId(), null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findByName - return EmploymentForm object when it exists")
    public void testFindByName() {
        doNothing().when(stringFieldValidator).validateNotNullNotEmpty(employmentForm.getName(), ENTITY_NAME, "name");
        when(employmentFormRepository.findByName(employmentForm.getName())).thenReturn(employmentForm);

        EmploymentForm result = employmentFormService.findByName(employmentForm.getName());

        assertEquals(employmentForm, result);
    }

    @Test
    @DisplayName("Test findByName - throw NoSuchElementFoundException when EmploymentForm object does not exist")
    public void testFindByNameNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, "name", employmentForm.getName());

        doNothing().when(stringFieldValidator).validateNotNullNotEmpty(employmentForm.getName(), ENTITY_NAME, "name");
        when(employmentFormRepository.findByName(employmentForm.getName())).thenReturn(null);

        try {
            employmentFormService.findByName(employmentForm.getName());
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
            employmentFormService.findByName(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test existsByName - return true when EmploymentForm object exists")
    public void testExistsByNameExists() {
        stringFieldValidator.validateNotNullNotEmpty(employmentForm.getName(), ENTITY_NAME, "name");
        when(employmentFormRepository.existsByName(employmentForm.getName())).thenReturn(true);

        assertTrue(employmentFormService.existsByName(employmentForm.getName()));
    }

    @Test
    @DisplayName("Test existsByName - return false when EmploymentForm object does not exist")
    public void testExistsByNameDoesNotExist() {
        stringFieldValidator.validateNotNullNotEmpty(employmentForm.getName(), ENTITY_NAME, "name");
        when(employmentFormRepository.existsByName(employmentForm.getName())).thenReturn(false);

        assertFalse(employmentFormService.existsByName(employmentForm.getName()));
    }

    @Test
    @DisplayName("Test existsByName - throw InvalidArgumentException when StringFieldValidator fails")
    public void testExistsByNameNull() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "name");

        doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateNotNullNotEmpty(null, ENTITY_NAME, "name");

        try {
            employmentFormService.existsByName(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test strictExistsByName - do nothing when EmploymentForm object exists")
    public void testStrictExistsByNameExists() {
        when(employmentFormRepository.existsByName(employmentForm.getName())).thenReturn(true);

        assertDoesNotThrow(() -> employmentFormService.strictExistsByName(employmentForm.getName()));
    }

    @Test
    @DisplayName("Test strictExistsByName - throw NoSuchElementFoundException when EmploymentForm object does not exist")
    public void testStrictExistsByNameDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFoundByName(ENTITY_NAME, employmentForm.getName());
        when(employmentFormRepository.existsByName(employmentForm.getName())).thenReturn(false);

        try {
            employmentFormService.strictExistsByName(employmentForm.getName());
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
            employmentFormService.strictExistsByName(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

}
