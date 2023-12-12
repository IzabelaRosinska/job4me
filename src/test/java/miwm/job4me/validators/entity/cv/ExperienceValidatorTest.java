package miwm.job4me.validators.entity.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.users.Employee;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.cv.ExperienceDto;
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
class ExperienceValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private ExperienceValidator experienceValidator;
    private Experience experience;
    private ExperienceDto experienceDto;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 300;
    private final String ENTITY_NAME = "Experience";
    private final String DESCRIPTION_FIELD_NAME = "description";

    @BeforeEach
    void setUp() {
        experience = Experience.builder()
                .id(1L)
                .employee(Employee.builder().id(1L).build())
                .description("test")
                .build();

        experienceDto = new ExperienceDto();
        experienceDto.setId(1L);
        experienceDto.setEmployeeId(1L);
        experienceDto.setDescription("test");
    }

    @Test
    @DisplayName("Validate dto - pass validation when dto is valid")
    void validateDtoValidDto() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(experienceDto.getDescription(), ENTITY_NAME, DESCRIPTION_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        assertDoesNotThrow(() -> experienceValidator.validateDto(experienceDto));
    }

    @Test
    @DisplayName("Validate dto - fail validation when dto is null")
    void validateDtoNullDto() {
        try {
            experienceValidator.validateDto(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate dto - fail validation when StringFieldValidator fails (description is null)")
    void validateDtoStringFieldValidatorFails() {
        experienceDto.setDescription(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DESCRIPTION_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(experienceDto.getDescription(), ENTITY_NAME, DESCRIPTION_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        try {
            experienceValidator.validateDto(experienceDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DESCRIPTION_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - pass validation when experience is valid")
    void validateValidExperience() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(experience.getDescription(), ENTITY_NAME, DESCRIPTION_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        assertDoesNotThrow(() -> experienceValidator.validate(experience));
    }

    @Test
    @DisplayName("test validate - fail validation when experience is null")
    void validateNullExperience() {
        try {
            experienceValidator.validate(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (description is null)")
    void validateStringFieldValidatorFails() {
        experience.setDescription(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DESCRIPTION_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(experience.getDescription(), ENTITY_NAME, DESCRIPTION_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        try {
            experienceValidator.validate(experience);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DESCRIPTION_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

}
