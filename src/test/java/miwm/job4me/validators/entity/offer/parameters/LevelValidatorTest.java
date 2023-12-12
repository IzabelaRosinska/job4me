package miwm.job4me.validators.entity.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.Level;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.LevelDto;
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
class LevelValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private LevelValidator levelValidator;
    private Level level;
    private LevelDto levelDto;
    private final int MIN_NAME_LENGTH = 1;
    private final int MAX_NAME_LENGTH = 20;
    private final String ENTITY_NAME = "Level";
    private final String DATA_FIELD_NAME = "name";

    @BeforeEach
    void setUp() {
        level = Level.builder()
                .id(1L)
                .name("test")
                .build();

        levelDto = new LevelDto();
        levelDto.setId(level.getId());
        levelDto.setName(level.getName());
    }

    @Test
    @DisplayName("Validate dto - pass validation when dto is valid")
    void validateDtoValidDto() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(levelDto.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        assertDoesNotThrow(() -> levelValidator.validateDto(levelDto));
    }

    @Test
    @DisplayName("Validate dto - fail validation when dto is null")
    void validateDtoNullDto() {
        try {
            levelValidator.validateDto(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate dto - fail validation when StringFieldValidator fails (Name is null)")
    void validateDtoStringFieldValidatorFails() {
        levelDto.setName(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(levelDto.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        try {
            levelValidator.validateDto(levelDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - pass validation when Level is valid")
    void validateValidLevel() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(level.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        assertDoesNotThrow(() -> levelValidator.validate(level));
    }

    @Test
    @DisplayName("test validate - fail validation when Level is null")
    void validateNullLevel() {
        try {
            levelValidator.validate(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (Name is null)")
    void validateStringFieldValidatorFails() {
        level.setName(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(level.getName(), ENTITY_NAME, DATA_FIELD_NAME, MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        try {
            levelValidator.validate(level);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }
}
