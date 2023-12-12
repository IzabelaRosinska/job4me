package miwm.job4me.validators.entity.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.Industry;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.IndustryDto;
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
class IndustryValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private IndustryValidator industryValidator;
    private Industry industry;
    private IndustryDto industryDto;
    private final int MIN_NAME_LENGTH = 1;
    private final int MAX_NAME_LENGTH = 100;
    private final String ENTITY_NAME = "Industry";
    private final String DATA_FIELD_NAME = "name";

    @BeforeEach
    void setUp() {
        industry = Industry.builder()
                .id(1L)
                .name("test")
                .build();

        industryDto = new IndustryDto();
        industryDto.setId(industry.getId());
        industryDto.setName(industry.getName());
    }

    @Test
    @DisplayName("Validate dto - pass validation when dto is valid")
    void validateDtoValidDto() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(industryDto.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        assertDoesNotThrow(() -> industryValidator.validateDto(industryDto));
    }

    @Test
    @DisplayName("Validate dto - fail validation when dto is null")
    void validateDtoNullDto() {
        try {
            industryValidator.validateDto(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate dto - fail validation when StringFieldValidator fails (Name is null)")
    void validateDtoStringFieldValidatorFails() {
        industryDto.setName(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(industryDto.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        try {
            industryValidator.validateDto(industryDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - pass validation when Industry is valid")
    void validateValidIndustry() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(industry.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        assertDoesNotThrow(() -> industryValidator.validate(industry));
    }

    @Test
    @DisplayName("test validate - fail validation when Industry is null")
    void validateNullIndustry() {
        try {
            industryValidator.validate(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (Name is null)")
    void validateStringFieldValidatorFails() {
        industry.setName(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(industry.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        try {
            industryValidator.validate(industry);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }
}
