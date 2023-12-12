package miwm.job4me.validators.entity.offer.description;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.description.ExtraSkill;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.ExtraSkillDto;
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
class ExtraSkillValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private ExtraSkillValidator extraSkillValidator;
    private ExtraSkill extraSkill;
    private ExtraSkillDto extraSkillDto;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 200;
    private final String ENTITY_NAME = "ExtraSkill";
    private final String DATA_FIELD_NAME = "description";

    @BeforeEach
    void setUp() {
        JobOffer jobOffer = JobOffer.builder().id(1L).build();

        extraSkill = ExtraSkill.builder()
                .id(1L)
                .description("test")
                .jobOffer(jobOffer)
                .build();

        extraSkillDto = new ExtraSkillDto();
        extraSkillDto.setId(extraSkill.getId());
        extraSkillDto.setDescription(extraSkill.getDescription());
    }

    @Test
    @DisplayName("Validate dto - pass validation when dto is valid")
    void validateDtoValidDto() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(extraSkillDto.getDescription(), ENTITY_NAME, DATA_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        assertDoesNotThrow(() -> extraSkillValidator.validateDto(extraSkillDto));
    }

    @Test
    @DisplayName("Validate dto - fail validation when dto is null")
    void validateDtoNullDto() {
        try {
            extraSkillValidator.validateDto(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate dto - fail validation when StringFieldValidator fails (description is null)")
    void validateDtoStringFieldValidatorFails() {
        extraSkillDto.setDescription(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(extraSkillDto.getDescription(), ENTITY_NAME, DATA_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        try {
            extraSkillValidator.validateDto(extraSkillDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - pass validation when ExtraSkill is valid")
    void validateValidExtraSkill() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(extraSkill.getDescription(), ENTITY_NAME, DATA_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        assertDoesNotThrow(() -> extraSkillValidator.validate(extraSkill));
    }

    @Test
    @DisplayName("test validate - fail validation when ExtraSkill is null")
    void validateNullExtraSkill() {
        try {
            extraSkillValidator.validate(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (description is null)")
    void validateStringFieldValidatorFails() {
        extraSkill.setDescription(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(extraSkill.getDescription(), ENTITY_NAME, DATA_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        try {
            extraSkillValidator.validate(extraSkill);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }
}
