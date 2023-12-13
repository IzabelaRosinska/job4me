package miwm.job4me.services.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.parameters.ContractType;
import miwm.job4me.repositories.offer.ContractTypeRepository;
import miwm.job4me.validators.entity.offer.parameters.ContractTypeValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.mappers.offer.parameters.ContractTypeMapper;
import miwm.job4me.web.model.offer.ContractTypeDto;
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
class ContractTypeServiceImplTest {
    @Mock
    private ContractTypeRepository contractTypeRepository;
    @Mock
    private ContractTypeMapper contractTypeMapper;
    @Mock
    private ContractTypeValidator contractTypeValidator;
    @Mock
    private IdValidator idValidator;
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private ContractTypeServiceImpl contractTypeService;

    private final String ENTITY_NAME = "ContractType";
    private final int MAX_DESCRIPTION_LENGTH = 25;

    private JobOffer jobOffer;
    private ContractType contractType1;
    private ContractType contractType2;
    private ContractTypeDto contractTypeDto1;
    private ContractTypeDto contractTypeDto2;

    @BeforeEach
    public void setUp() {
        contractType1 = ContractType
                .builder()
                .id(1L)
                .name("name1")
                .build();

        contractTypeDto1 = new ContractTypeDto();
        contractTypeDto1.setId(contractType1.getId());
        contractTypeDto1.setName(contractType1.getName());

        contractType2 = ContractType
                .builder()
                .id(2L)
                .name("name2")
                .build();

        contractTypeDto2 = new ContractTypeDto();
        contractTypeDto2.setId(contractType2.getId());
    }

    @Test
    @DisplayName("Test existsById - return true when ContractType object exists")
    public void testExistsByIdExists() {
        when(contractTypeRepository.existsById(contractType1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(contractType1.getId(), ENTITY_NAME);

        assertTrue(contractTypeService.existsById(contractType1.getId()));
    }

    @Test
    @DisplayName("Test existsById - return false when ContractType object does not exist")
    public void testExistsByIdDoesNotExist() {
        when(contractTypeRepository.existsById(contractType1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(contractType1.getId(), ENTITY_NAME);

        assertFalse(contractTypeService.existsById(contractType1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - do nothing when ContractType object exists")
    public void testStrictExistsByIdExists() {
        when(contractTypeRepository.existsById(contractType1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(contractType1.getId(), ENTITY_NAME);

        assertDoesNotThrow(() -> contractTypeService.strictExistsById(contractType1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - throw NoSuchElementFoundException when ContractType object does not exist")
    public void testStrictExistsByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, contractType1.getId());

        when(contractTypeRepository.existsById(contractType1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(contractType1.getId(), ENTITY_NAME);

        try {
            contractTypeService.strictExistsById(contractType1.getId());
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
            contractTypeService.strictExistsById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAll - return all existing ContractType objects")
    public void testFindAll() {
        when(contractTypeRepository.findAll()).thenReturn(List.of(contractType1, contractType2));
        when(contractTypeMapper.toDto(contractType1)).thenReturn(contractTypeDto1);
        when(contractTypeMapper.toDto(contractType2)).thenReturn(contractTypeDto2);

        Set<ContractTypeDto> result = contractTypeService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(contractTypeDto1));
        assertTrue(result.contains(contractTypeDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set when there are no ContractType objects")
    public void testFindAllEmpty() {
        when(contractTypeRepository.findAll()).thenReturn(List.of());

        Set<ContractTypeDto> result = contractTypeService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return ContractType object when it exists")
    public void testFindById() {
        when(contractTypeRepository.findById(contractType1.getId())).thenReturn(java.util.Optional.of(contractType1));
        when(contractTypeMapper.toDto(contractType1)).thenReturn(contractTypeDto1);

        ContractTypeDto result = contractTypeService.findById(contractType1.getId());

        assertEquals(contractTypeDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException when ContractType object does not exist")
    public void testFindByIdNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound("ContractType", contractType1.getId());
        when(contractTypeRepository.findById(contractType1.getId())).thenReturn(java.util.Optional.empty());

        try {
            contractTypeService.findById(contractType1.getId());
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
            contractTypeService.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return ContractType object when it is valid")
    public void testSaveValid() {
        when(contractTypeRepository.save(contractType1)).thenReturn(contractType1);
        when(contractTypeMapper.toDto(contractType1)).thenReturn(contractTypeDto1);
        doNothing().when(contractTypeValidator).validate(contractType1);

        ContractTypeDto result = contractTypeService.save(contractType1);

        assertEquals(contractTypeDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when ContractType object is null")
    public void testSaveThrowExceptionNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(contractTypeValidator).validate(null);

        try {
            contractTypeService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when ContractTypeValidator fails")
    public void testSaveThrowExceptionContractTypeValidatorFails() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH);

        contractType1.setName("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(contractTypeValidator).validate(contractType1);

        try {
            contractTypeService.save(contractType1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - ContractType object exists")
    public void testDeleteContractTypeExists() {
        when(contractTypeRepository.existsById(contractType1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(contractType1.getId(), ENTITY_NAME);
        doNothing().when(contractTypeRepository).delete(contractType1);

        assertDoesNotThrow(() -> contractTypeService.delete(contractType1));
    }

    @Test
    @DisplayName("Test delete - ContractType object does not exist")
    public void testDeleteContractTypeDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, contractType1.getId());
        when(contractTypeRepository.existsById(contractType1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(contractType1.getId(), ENTITY_NAME);

        try {
            contractTypeService.delete(contractType1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - ContractType object is null")
    public void testDeleteContractTypeNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            contractTypeService.delete(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - ContractType id is null")
    public void testDeleteContractTypeIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);
        contractType1.setId(null);

        try {
            contractTypeService.delete(contractType1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - ContractType object exists")
    public void testDeleteByIdContractTypeExists() {
        when(contractTypeRepository.existsById(contractType1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(contractType1.getId(), ENTITY_NAME);
        doNothing().when(contractTypeRepository).deleteById(contractType1.getId());

        assertDoesNotThrow(() -> contractTypeService.deleteById(contractType1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - ContractType object does not exist")
    public void testDeleteByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, contractType1.getId());

        when(contractTypeRepository.existsById(contractType1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(contractType1.getId(), ENTITY_NAME);

        try {
            contractTypeService.deleteById(contractType1.getId());
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
            contractTypeService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - update and return ContractTypeDto object when it is valid")
    public void testUpdateContractTypeExists() {
        doNothing().when(idValidator).validateLongId(contractType1.getId(), ENTITY_NAME);
        when(contractTypeRepository.existsById(contractType1.getId())).thenReturn(true);
        doNothing().when(contractTypeValidator).validateDto(contractTypeDto1);
        when(contractTypeMapper.toEntity(contractTypeDto1)).thenReturn(contractType1);
        when(contractTypeRepository.save(contractType1)).thenReturn(contractType1);
        when(contractTypeMapper.toDto(contractType1)).thenReturn(contractTypeDto1);

        ContractTypeDto result = contractTypeService.update(contractTypeDto1.getId(), contractTypeDto1);

        assertEquals(contractTypeDto1, result);
    }

    @Test
    @DisplayName("Test update - throw NoSuchElementFoundException when IdValidator fails")
    public void testUpdateContractTypeDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, contractType1.getId());
        doThrow(new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, contractType1.getId()))).when(idValidator).validateLongId(contractType1.getId(), ENTITY_NAME);

        try {
            contractTypeService.update(contractType1.getId(), contractTypeDto1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - throw InvalidArgumentException when ContractTypeDto object is null and ContractTypeValidator fails")
    public void testUpdateContractTypeDtoNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        when(contractTypeRepository.existsById(contractType1.getId())).thenReturn(true);
        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(contractTypeValidator).validateDto(null);

        try {
            contractTypeService.update(contractType1.getId(), null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findByName - return ContractType object when it exists")
    public void testFindByName() {
        doNothing().when(stringFieldValidator).validateNotNullNotEmpty(contractType1.getName(), ENTITY_NAME, "name");
        when(contractTypeRepository.findByName(contractType1.getName())).thenReturn(contractType1);

        ContractType result = contractTypeService.findByName(contractType1.getName());

        assertEquals(contractType1, result);
    }

    @Test
    @DisplayName("Test findByName - throw NoSuchElementFoundException when ContractType object does not exist")
    public void testFindByNameNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, "name", contractType1.getName());

        doNothing().when(stringFieldValidator).validateNotNullNotEmpty(contractType1.getName(), ENTITY_NAME, "name");
        when(contractTypeRepository.findByName(contractType1.getName())).thenReturn(null);

        try {
            contractTypeService.findByName(contractType1.getName());
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
            contractTypeService.findByName(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test existsByName - return true when ContractType object exists")
    public void testExistsByNameExists() {
        stringFieldValidator.validateNotNullNotEmpty(contractType1.getName(), ENTITY_NAME, "name");
        when(contractTypeRepository.existsByName(contractType1.getName())).thenReturn(true);

        assertTrue(contractTypeService.existsByName(contractType1.getName()));
    }

    @Test
    @DisplayName("Test existsByName - return false when ContractType object does not exist")
    public void testExistsByNameDoesNotExist() {
        stringFieldValidator.validateNotNullNotEmpty(contractType1.getName(), ENTITY_NAME, "name");
        when(contractTypeRepository.existsByName(contractType1.getName())).thenReturn(false);

        assertFalse(contractTypeService.existsByName(contractType1.getName()));
    }

    @Test
    @DisplayName("Test existsByName - throw InvalidArgumentException when StringFieldValidator fails")
    public void testExistsByNameNull() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(ENTITY_NAME, "name");

        doThrow(new InvalidArgumentException(expectedMessage)).when(stringFieldValidator).validateNotNullNotEmpty(null, ENTITY_NAME, "name");

        try {
            contractTypeService.existsByName(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test strictExistsByName - do nothing when ContractType object exists")
    public void testStrictExistsByNameExists() {
        when(contractTypeRepository.existsByName(contractType1.getName())).thenReturn(true);

        assertDoesNotThrow(() -> contractTypeService.strictExistsByName(contractType1.getName()));
    }

    @Test
    @DisplayName("Test strictExistsByName - throw NoSuchElementFoundException when ContractType object does not exist")
    public void testStrictExistsByNameDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFoundByName(ENTITY_NAME, contractType1.getName());
        when(contractTypeRepository.existsByName(contractType1.getName())).thenReturn(false);

        try {
            contractTypeService.strictExistsByName(contractType1.getName());
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
            contractTypeService.strictExistsByName(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

}
