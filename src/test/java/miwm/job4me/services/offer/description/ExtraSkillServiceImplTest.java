package miwm.job4me.services.offer.description;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.description.ExtraSkill;
import miwm.job4me.repositories.offer.ExtraSkillRepository;
import miwm.job4me.validators.entity.offer.description.ExtraSkillValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.offer.description.ExtraSkillMapper;
import miwm.job4me.web.model.offer.ExtraSkillDto;
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
class ExtraSkillServiceImplTest {
    @Mock
    private ExtraSkillRepository extraSkillRepository;
    @Mock
    private ExtraSkillMapper extraSkillMapper;
    @Mock
    private ExtraSkillValidator extraSkillValidator;
    @Mock
    private IdValidator idValidator;
    @InjectMocks
    private ExtraSkillServiceImpl extraSkillService;

    private final String ENTITY_NAME = "ExtraSkill";
    private final int MAX_DESCRIPTION_LENGTH = 200;

    private JobOffer jobOffer;
    private ExtraSkill extraSkill1;
    private ExtraSkill extraSkill2;
    private ExtraSkillDto extraSkillDto1;
    private ExtraSkillDto extraSkillDto2;

    @BeforeEach
    public void setUp() {
        jobOffer = JobOffer
                .builder()
                .id(1L)
                .build();

        extraSkill1 = ExtraSkill
                .builder()
                .id(1L)
                .description("description1")
                .jobOffer(jobOffer)
                .build();

        extraSkillDto1 = new ExtraSkillDto();
        extraSkillDto1.setId(extraSkill1.getId());
        extraSkillDto1.setDescription(extraSkill1.getDescription());

        extraSkill2 = ExtraSkill
                .builder()
                .id(2L)
                .description("description2")
                .jobOffer(jobOffer)
                .build();

        extraSkillDto2 = new ExtraSkillDto();
        extraSkillDto2.setId(extraSkill2.getId());
        extraSkillDto2.setDescription(extraSkill2.getDescription());
    }

    @Test
    @DisplayName("Test existsById - return true when ExtraSkill object exists")
    public void testExistsByIdExists() {
        when(extraSkillRepository.existsById(extraSkill1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(extraSkill1.getId(), ENTITY_NAME);

        assertTrue(extraSkillService.existsById(extraSkill1.getId()));
    }

    @Test
    @DisplayName("Test existsById - return false when ExtraSkill object does not exist")
    public void testExistsByIdDoesNotExist() {
        when(extraSkillRepository.existsById(extraSkill1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(extraSkill1.getId(), ENTITY_NAME);

        assertFalse(extraSkillService.existsById(extraSkill1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - do nothing when ExtraSkill object exists")
    public void testStrictExistsByIdExists() {
        when(extraSkillRepository.existsById(extraSkill1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(extraSkill1.getId(), ENTITY_NAME);

        assertDoesNotThrow(() -> extraSkillService.strictExistsById(extraSkill1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - throw NoSuchElementFoundException when ExtraSkill object does not exist")
    public void testStrictExistsByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, extraSkill1.getId());

        when(extraSkillRepository.existsById(extraSkill1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(extraSkill1.getId(), ENTITY_NAME);

        try {
            extraSkillService.strictExistsById(extraSkill1.getId());
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
            extraSkillService.strictExistsById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAll - return all existing ExtraSkill objects")
    public void testFindAll() {
        when(extraSkillRepository.findAll()).thenReturn(List.of(extraSkill1, extraSkill2));
        when(extraSkillMapper.toDto(extraSkill1)).thenReturn(extraSkillDto1);
        when(extraSkillMapper.toDto(extraSkill2)).thenReturn(extraSkillDto2);

        Set<ExtraSkillDto> result = extraSkillService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(extraSkillDto1));
        assertTrue(result.contains(extraSkillDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set when there are no ExtraSkill objects")
    public void testFindAllEmpty() {
        when(extraSkillRepository.findAll()).thenReturn(List.of());

        Set<ExtraSkillDto> result = extraSkillService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return ExtraSkill object when it exists")
    public void testFindById() {
        when(extraSkillRepository.findById(extraSkill1.getId())).thenReturn(java.util.Optional.of(extraSkill1));
        when(extraSkillMapper.toDto(extraSkill1)).thenReturn(extraSkillDto1);

        ExtraSkillDto result = extraSkillService.findById(extraSkill1.getId());

        assertEquals(extraSkillDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException when ExtraSkill object does not exist")
    public void testFindByIdNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound("ExtraSkill", extraSkill1.getId());
        when(extraSkillRepository.findById(extraSkill1.getId())).thenReturn(java.util.Optional.empty());

        try {
            extraSkillService.findById(extraSkill1.getId());
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
            extraSkillService.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return ExtraSkill object when it is valid")
    public void testSaveValid() {
        when(extraSkillRepository.save(extraSkill1)).thenReturn(extraSkill1);
        when(extraSkillMapper.toDto(extraSkill1)).thenReturn(extraSkillDto1);
        doNothing().when(extraSkillValidator).validate(extraSkill1);

        ExtraSkillDto result = extraSkillService.save(extraSkill1);

        assertEquals(extraSkillDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when ExtraSkill object is null")
    public void testSaveThrowExceptionNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(extraSkillValidator).validate(null);

        try {
            extraSkillService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when ExtraSkillValidator fails")
    public void testSaveThrowExceptionExtraSkillValidatorFails() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH);

        extraSkill1.setDescription("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(extraSkillValidator).validate(extraSkill1);

        try {
            extraSkillService.save(extraSkill1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - ExtraSkill object exists")
    public void testDeleteExtraSkillExists() {
        when(extraSkillRepository.existsById(extraSkill1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(extraSkill1.getId(), ENTITY_NAME);
        doNothing().when(extraSkillRepository).delete(extraSkill1);

        assertDoesNotThrow(() -> extraSkillService.delete(extraSkill1));
    }

    @Test
    @DisplayName("Test delete - ExtraSkill object does not exist")
    public void testDeleteExtraSkillDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, extraSkill1.getId());
        when(extraSkillRepository.existsById(extraSkill1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(extraSkill1.getId(), ENTITY_NAME);

        try {
            extraSkillService.delete(extraSkill1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - ExtraSkill object is null")
    public void testDeleteExtraSkillNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            extraSkillService.delete(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - ExtraSkill id is null")
    public void testDeleteExtraSkillIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);
        extraSkill1.setId(null);

        try {
            extraSkillService.delete(extraSkill1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - ExtraSkill object exists")
    public void testDeleteByIdExtraSkillExists() {
        when(extraSkillRepository.existsById(extraSkill1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(extraSkill1.getId(), ENTITY_NAME);
        doNothing().when(extraSkillRepository).deleteById(extraSkill1.getId());

        assertDoesNotThrow(() -> extraSkillService.deleteById(extraSkill1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - ExtraSkill object does not exist")
    public void testDeleteByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, extraSkill1.getId());

        when(extraSkillRepository.existsById(extraSkill1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(extraSkill1.getId(), ENTITY_NAME);

        try {
            extraSkillService.deleteById(extraSkill1.getId());
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
            extraSkillService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - update and return ExtraSkillDto object when it is valid")
    public void testUpdateExtraSkillExists() {
        doNothing().when(idValidator).validateLongId(extraSkill1.getId(), ENTITY_NAME);
        when(extraSkillRepository.existsById(extraSkill1.getId())).thenReturn(true);
        doNothing().when(extraSkillValidator).validateDto(extraSkillDto1);
        when(extraSkillMapper.toEntity(extraSkillDto1)).thenReturn(extraSkill1);
        when(extraSkillRepository.save(extraSkill1)).thenReturn(extraSkill1);
        when(extraSkillMapper.toDto(extraSkill1)).thenReturn(extraSkillDto1);

        ExtraSkillDto result = extraSkillService.update(extraSkillDto1.getId(), extraSkillDto1);

        assertEquals(extraSkillDto1, result);
    }

    @Test
    @DisplayName("Test update - throw NoSuchElementFoundException when IdValidator fails")
    public void testUpdateExtraSkillDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, extraSkill1.getId());
        doThrow(new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, extraSkill1.getId()))).when(idValidator).validateLongId(extraSkill1.getId(), ENTITY_NAME);

        try {
            extraSkillService.update(extraSkill1.getId(), extraSkillDto1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - throw InvalidArgumentException when ExtraSkillDto object is null and ExtraSkillValidator fails")
    public void testUpdateExtraSkillDtoNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        when(extraSkillRepository.existsById(extraSkill1.getId())).thenReturn(true);
        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(extraSkillValidator).validateDto(null);

        try {
            extraSkillService.update(extraSkill1.getId(), null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAllByJobOfferId - return all existing ExtraSkill objects for given JobOffer")
    public void testFindAllByJobOfferId() {
        when(extraSkillRepository.findAllByJobOfferId(jobOffer.getId())).thenReturn(List.of(extraSkill1, extraSkill2));
        when(extraSkillMapper.toDto(extraSkill1)).thenReturn(extraSkillDto1);
        when(extraSkillMapper.toDto(extraSkill2)).thenReturn(extraSkillDto2);

        List<ExtraSkillDto> result = extraSkillService.findAllByJobOfferId(jobOffer.getId());

        assertEquals(2, result.size());
        assertTrue(result.contains(extraSkillDto1));
        assertTrue(result.contains(extraSkillDto2));
    }

    @Test
    @DisplayName("Test findAllByJobOfferId - return empty set when there are no ExtraSkill objects for given JobOffer")
    public void testFindAllByJobOfferIdEmpty() {
        when(extraSkillRepository.findAllByJobOfferId(jobOffer.getId())).thenReturn(List.of());

        List<ExtraSkillDto> result = extraSkillService.findAllByJobOfferId(jobOffer.getId());

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findAllByJobOfferId - throw InvalidArgumentException when id is null")
    public void testFindAllByJobOfferIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            extraSkillService.findAllByJobOfferId(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test deleteAllByJobOfferId - delete all ExtraSkill objects for given JobOffer")
    public void testDeleteAllByJobOfferId() {
        doNothing().when(idValidator).validateLongId(jobOffer.getId(), ENTITY_NAME);
        doNothing().when(extraSkillRepository).deleteAllByJobOfferId(jobOffer.getId());

        assertDoesNotThrow(() -> extraSkillService.deleteAllByJobOfferId(jobOffer.getId()));
    }

    @Test
    @DisplayName("Test deleteAllByJobOfferId - throw InvalidArgumentException when id is null")
    public void testDeleteAllByJobOfferIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);
        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            extraSkillService.deleteAllByJobOfferId(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

}
