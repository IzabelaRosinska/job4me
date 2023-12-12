package miwm.job4me.validators.entity.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.EmploymentForm;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.EmploymentFormDto;
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
class EmploymentFormValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private EmploymentFormValidator employmentFormValidator;
    private EmploymentForm employmentForm;
    private EmploymentFormDto EmploymentFormDto;
    private final int MIN_NAME_LENGTH = 1;
    private final int MAX_NAME_LENGTH = 25;
    private final String ENTITY_NAME = "EmploymentForm";
    private final String DATA_FIELD_NAME = "name";

    @BeforeEach
    void setUp() {
        employmentForm = EmploymentForm.builder()
                .id(1L)
                .name("test")
                .build();

        EmploymentFormDto = new EmploymentFormDto();
        EmploymentFormDto.setId(employmentForm.getId());
        EmploymentFormDto.setName(employmentForm.getName());
    }

    @Test
    @DisplayName("Validate dto - pass validation when dto is valid")
    void validateDtoValidDto() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(EmploymentFormDto.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        assertDoesNotThrow(() -> employmentFormValidator.validateDto(EmploymentFormDto));
    }

    @Test
    @DisplayName("Validate dto - fail validation when dto is null")
    void validateDtoNullDto() {
        try {
            employmentFormValidator.validateDto(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate dto - fail validation when StringFieldValidator fails (Name is null)")
    void validateDtoStringFieldValidatorFails() {
        EmploymentFormDto.setName(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(EmploymentFormDto.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        try {
            employmentFormValidator.validateDto(EmploymentFormDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - pass validation when EmploymentForm is valid")
    void validateValidEmploymentForm() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(employmentForm.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        assertDoesNotThrow(() -> employmentFormValidator.validate(employmentForm));
    }

    @Test
    @DisplayName("test validate - fail validation when EmploymentForm is null")
    void validateNullEmploymentForm() {
        try {
            employmentFormValidator.validate(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (Name is null)")
    void validateStringFieldValidatorFails() {
        employmentForm.setName(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(employmentForm.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        try {
            employmentFormValidator.validate(employmentForm);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

}
