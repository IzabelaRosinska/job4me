package miwm.job4me.services.offer.description;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.description.Requirement;
import miwm.job4me.repositories.offer.RequirementRepository;
import miwm.job4me.validators.entity.offer.description.RequirementValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.offer.description.RequirementMapper;
import miwm.job4me.web.model.offer.RequirementDto;
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
class RequirementServiceImplTest {
    @Mock
    private RequirementRepository requirementRepository;
    @Mock
    private RequirementMapper requirementMapper;
    @Mock
    private RequirementValidator requirementValidator;
    @Mock
    private IdValidator idValidator;
    @InjectMocks
    private RequirementServiceImpl requirementService;

    private final String ENTITY_NAME = "Requirement";
    private final int MAX_DESCRIPTION_LENGTH = 250;

    private JobOffer jobOffer;
    private Requirement requirement1;
    private Requirement requirement2;
    private RequirementDto requirementDto;
    private RequirementDto requirementDto2;

    @BeforeEach
    public void setUp() {
        jobOffer = JobOffer
                .builder()
                .id(1L)
                .build();

        requirement1 = Requirement
                .builder()
                .id(1L)
                .description("description1")
                .jobOffer(jobOffer)
                .build();

        requirementDto = new RequirementDto();
        requirementDto.setId(requirement1.getId());
        requirementDto.setDescription(requirement1.getDescription());

        requirement2 = Requirement
                .builder()
                .id(2L)
                .description("description2")
                .jobOffer(jobOffer)
                .build();

        requirementDto2 = new RequirementDto();
        requirementDto2.setId(requirement2.getId());
        requirementDto2.setDescription(requirement2.getDescription());
    }

    @Test
    @DisplayName("Test existsById - return true when Requirement object exists")
    public void testExistsByIdExists() {
        when(requirementRepository.existsById(requirement1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(requirement1.getId(), ENTITY_NAME);

        assertTrue(requirementService.existsById(requirement1.getId()));
    }

    @Test
    @DisplayName("Test existsById - return false when Requirement object does not exist")
    public void testExistsByIdDoesNotExist() {
        when(requirementRepository.existsById(requirement1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(requirement1.getId(), ENTITY_NAME);

        assertFalse(requirementService.existsById(requirement1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - do nothing when Requirement object exists")
    public void testStrictExistsByIdExists() {
        when(requirementRepository.existsById(requirement1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(requirement1.getId(), ENTITY_NAME);

        assertDoesNotThrow(() -> requirementService.strictExistsById(requirement1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - throw NoSuchElementFoundException when Requirement object does not exist")
    public void testStrictExistsByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, requirement1.getId());

        when(requirementRepository.existsById(requirement1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(requirement1.getId(), ENTITY_NAME);

        try {
            requirementService.strictExistsById(requirement1.getId());
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
            requirementService.strictExistsById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAll - return all existing Requirement objects")
    public void testFindAll() {
        when(requirementRepository.findAll()).thenReturn(List.of(requirement1, requirement2));
        when(requirementMapper.toDto(requirement1)).thenReturn(requirementDto);
        when(requirementMapper.toDto(requirement2)).thenReturn(requirementDto2);

        Set<RequirementDto> result = requirementService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(requirementDto));
        assertTrue(result.contains(requirementDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set when there are no Requirement objects")
    public void testFindAllEmpty() {
        when(requirementRepository.findAll()).thenReturn(List.of());

        Set<RequirementDto> result = requirementService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return Requirement object when it exists")
    public void testFindById() {
        when(requirementRepository.findById(requirement1.getId())).thenReturn(java.util.Optional.of(requirement1));
        when(requirementMapper.toDto(requirement1)).thenReturn(requirementDto);

        RequirementDto result = requirementService.findById(requirement1.getId());

        assertEquals(requirementDto, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException when Requirement object does not exist")
    public void testFindByIdNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound("Requirement", requirement1.getId());
        when(requirementRepository.findById(requirement1.getId())).thenReturn(java.util.Optional.empty());

        try {
            requirementService.findById(requirement1.getId());
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
            requirementService.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return Requirement object when it is valid")
    public void testSaveValid() {
        when(requirementRepository.save(requirement1)).thenReturn(requirement1);
        when(requirementMapper.toDto(requirement1)).thenReturn(requirementDto);
        doNothing().when(requirementValidator).validate(requirement1);

        RequirementDto result = requirementService.save(requirement1);

        assertEquals(requirementDto, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when Requirement object is null")
    public void testSaveThrowExceptionNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(requirementValidator).validate(null);

        try {
            requirementService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when RequirementValidator fails")
    public void testSaveThrowExceptionRequirementValidatorFails() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH);

        requirement1.setDescription("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(requirementValidator).validate(requirement1);

        try {
            requirementService.save(requirement1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Requirement object exists")
    public void testDeleteRequirementExists() {
        when(requirementRepository.existsById(requirement1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(requirement1.getId(), ENTITY_NAME);
        doNothing().when(requirementRepository).delete(requirement1);

        assertDoesNotThrow(() -> requirementService.delete(requirement1));
    }

    @Test
    @DisplayName("Test delete - Requirement object does not exist")
    public void testDeleteRequirementDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, requirement1.getId());
        when(requirementRepository.existsById(requirement1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(requirement1.getId(), ENTITY_NAME);

        try {
            requirementService.delete(requirement1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Requirement object is null")
    public void testDeleteRequirementNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            requirementService.delete(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Requirement id is null")
    public void testDeleteRequirementIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);
        requirement1.setId(null);

        try {
            requirementService.delete(requirement1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - Requirement object exists")
    public void testDeleteByIdRequirementExists() {
        when(requirementRepository.existsById(requirement1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(requirement1.getId(), ENTITY_NAME);
        doNothing().when(requirementRepository).deleteById(requirement1.getId());

        assertDoesNotThrow(() -> requirementService.deleteById(requirement1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - Requirement object does not exist")
    public void testDeleteByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, requirement1.getId());

        when(requirementRepository.existsById(requirement1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(requirement1.getId(), ENTITY_NAME);

        try {
            requirementService.deleteById(requirement1.getId());
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
            requirementService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - update and return RequirementDto object when it is valid")
    public void testUpdateRequirementExists() {
        doNothing().when(idValidator).validateLongId(requirement1.getId(), ENTITY_NAME);
        when(requirementRepository.existsById(requirement1.getId())).thenReturn(true);
        doNothing().when(requirementValidator).validateDto(requirementDto);
        when(requirementMapper.toEntity(requirementDto)).thenReturn(requirement1);
        when(requirementRepository.save(requirement1)).thenReturn(requirement1);
        when(requirementMapper.toDto(requirement1)).thenReturn(requirementDto);

        RequirementDto result = requirementService.update(requirementDto.getId(), requirementDto);

        assertEquals(requirementDto, result);
    }

    @Test
    @DisplayName("Test update - throw NoSuchElementFoundException when IdValidator fails")
    public void testUpdateRequirementDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, requirement1.getId());
        doThrow(new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, requirement1.getId()))).when(idValidator).validateLongId(requirement1.getId(), ENTITY_NAME);

        try {
            requirementService.update(requirement1.getId(), requirementDto);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - throw InvalidArgumentException when RequirementDto object is null and RequirementValidator fails")
    public void testUpdateRequirementDtoNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        when(requirementRepository.existsById(requirement1.getId())).thenReturn(true);
        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(requirementValidator).validateDto(null);

        try {
            requirementService.update(requirement1.getId(), null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAllByJobOfferId - return all existing Requirement objects for given JobOffer")
    public void testFindAllByJobOfferId() {
        when(requirementRepository.findAllByJobOfferId(jobOffer.getId())).thenReturn(List.of(requirement1, requirement2));
        when(requirementMapper.toDto(requirement1)).thenReturn(requirementDto);
        when(requirementMapper.toDto(requirement2)).thenReturn(requirementDto2);

        List<RequirementDto> result = requirementService.findAllByJobOfferId(jobOffer.getId());

        assertEquals(2, result.size());
        assertTrue(result.contains(requirementDto));
        assertTrue(result.contains(requirementDto2));
    }

    @Test
    @DisplayName("Test findAllByJobOfferId - return empty set when there are no Requirement objects for given JobOffer")
    public void testFindAllByJobOfferIdEmpty() {
        when(requirementRepository.findAllByJobOfferId(jobOffer.getId())).thenReturn(List.of());

        List<RequirementDto> result = requirementService.findAllByJobOfferId(jobOffer.getId());

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findAllByJobOfferId - throw InvalidArgumentException when id is null")
    public void testFindAllByJobOfferIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            requirementService.findAllByJobOfferId(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test deleteAllByJobOfferId - delete all Requirement objects for given JobOffer")
    public void testDeleteAllByJobOfferId() {
        doNothing().when(idValidator).validateLongId(jobOffer.getId(), ENTITY_NAME);
        doNothing().when(requirementRepository).deleteAllByJobOfferId(jobOffer.getId());

        assertDoesNotThrow(() -> requirementService.deleteAllByJobOfferId(jobOffer.getId()));
    }

    @Test
    @DisplayName("Test deleteAllByJobOfferId - throw InvalidArgumentException when id is null")
    public void testDeleteAllByJobOfferIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);
        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            requirementService.deleteAllByJobOfferId(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
