package miwm.job4me.validators.entity.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.model.users.Employee;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.cv.SkillDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SkillValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private SkillValidator skillValidator;
    private Skill skill;
    private SkillDto skillDto;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 50;
    private final String ENTITY_NAME = "Skill";
    private final String DESCRIPTION_FIELD_NAME = "description";

    @BeforeEach
    void setUp() {
        skill = Skill.builder()
                .id(1L)
                .employee(Employee.builder().id(1L).build())
                .description("test")
                .build();

        skillDto = new SkillDto();
        skillDto.setId(1L);
        skillDto.setEmployeeId(1L);
        skillDto.setDescription("test");
    }

    @Test
    @DisplayName("Validate dto - pass validation when dto is valid")
    void validateDtoValidDto() {
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(skillDto.getDescription(), ENTITY_NAME, DESCRIPTION_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        assertDoesNotThrow(() -> skillValidator.validateDto(skillDto));
    }

    @Test
    @DisplayName("Validate dto - fail validation when dto is null")
    void validateDtoNullDto() {
        try {
            skillValidator.validateDto(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate dto - fail validation when DescriptionValidator fails (description is null)")
    void validateDtoDescriptionValidatorFails() {
        skillDto.setDescription(null);

        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DESCRIPTION_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(skillDto.getDescription(), ENTITY_NAME, DESCRIPTION_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        try {
            skillValidator.validateDto(skillDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DESCRIPTION_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }
}