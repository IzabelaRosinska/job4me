package miwm.job4me.validators.entity.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.ContractType;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.ContractTypeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ContractTypeValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private ContractTypeValidator contractTypeValidator;
    private ContractType contractType;
    private ContractTypeDto contractTypeDto;
    private final int MIN_NAME_LENGTH = 1;
    private final int MAX_NAME_LENGTH = 25;
    private final String ENTITY_NAME = "ContractType";
    private final String DATA_FIELD_NAME = "name";

    @BeforeEach
    void setUp() {
        contractType = contractType.builder()
                .id(1L)
                .name("test")
                .build();

        contractTypeDto = new ContractTypeDto();
        contractTypeDto.setId(contractType.getId());
        contractTypeDto.setName(contractType.getName());
    }

    @Test
    @DisplayName("Validate dto - pass validation when dto is valid")
    void validateDtoValidDto() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(contractTypeDto.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        assertDoesNotThrow(() -> contractTypeValidator.validateDto(contractTypeDto));
    }

    @Test
    @DisplayName("Validate dto - fail validation when dto is null")
    void validateDtoNullDto() {
        try {
            contractTypeValidator.validateDto(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate dto - fail validation when StringFieldValidator fails (Name is null)")
    void validateDtoStringFieldValidatorFails() {
        contractTypeDto.setName(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(contractTypeDto.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        try {
            contractTypeValidator.validateDto(contractTypeDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - pass validation when ContractType is valid")
    void validateValidContractType() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(contractType.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        assertDoesNotThrow(() -> contractTypeValidator.validate(contractType));
    }

    @Test
    @DisplayName("test validate - fail validation when ContractType is null")
    void validateNullContractType() {
        try {
            contractTypeValidator.validate(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (Name is null)")
    void validateStringFieldValidatorFails() {
        contractType.setName(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(contractType.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        try {
            contractTypeValidator.validate(contractType);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

}
