package miwm.job4me.validators.entity.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.Localization;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.LocalizationDto;
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
class LocalizationValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private LocalizationValidator localizationValidator;
    private Localization localization;
    private LocalizationDto localizationDto;
    private final int MIN_NAME_LENGTH = 1;
    private final int MAX_NAME_LENGTH = 50;
    private final String ENTITY_NAME = "Localization";
    private final String DATA_FIELD_NAME = "city";

    @BeforeEach
    void setUp() {
        localization = Localization.builder()
                .id(1L)
                .city("test")
                .build();

        localizationDto = new LocalizationDto();
        localizationDto.setId(localization.getId());
        localizationDto.setCity(localization.getCity());
    }

    @Test
    @DisplayName("Validate dto - pass validation when dto is valid")
    void validateDtoValidDto() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(localizationDto.getCity(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        assertDoesNotThrow(() -> localizationValidator.validateDto(localizationDto));
    }

    @Test
    @DisplayName("Validate dto - fail validation when dto is null")
    void validateDtoNullDto() {
        try {
            localizationValidator.validateDto(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate dto - fail validation when StringFieldValidator fails (Name is null)")
    void validateDtoStringFieldValidatorFails() {
        localizationDto.setCity(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(localizationDto.getCity(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        try {
            localizationValidator.validateDto(localizationDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - pass validation when Localization is valid")
    void validateValidLocalization() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(localization.getCity(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        assertDoesNotThrow(() -> localizationValidator.validate(localization));
    }

    @Test
    @DisplayName("test validate - fail validation when Localization is null")
    void validateNullLocalization() {
        try {
            localizationValidator.validate(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (Name is null)")
    void validateStringFieldValidatorFails() {
        localization.setCity(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(localization.getCity(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        try {
            localizationValidator.validate(localization);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }
}
