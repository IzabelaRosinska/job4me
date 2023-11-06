package miwm.job4me.validators.entity.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Education;
import miwm.job4me.model.users.Employee;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.cv.EducationDto;
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
class EducationValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private EducationValidator educationValidator;
    private Education education;
    private EducationDto educationDto;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 150;
    private final String ENTITY_NAME = "Education";
    private final String DESCRIPTION_FIELD_NAME = "description";

    @BeforeEach
    void setUp() {
        education = Education.builder()
                .id(1L)
                .employee(Employee.builder().id(1L).build())
                .description("test")
                .build();

        educationDto = new EducationDto();
        educationDto.setId(1L);
        educationDto.setEmployeeId(1L);
        educationDto.setDescription("test");
    }

    @Test
    @DisplayName("Validate dto - pass validation when dto is valid")
    void validateDtoValidDto() {
        Mockito.doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(educationDto.getDescription(), ENTITY_NAME, DESCRIPTION_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        assertDoesNotThrow(() -> educationValidator.validateDto(educationDto));
    }

    @Test
    @DisplayName("Validate dto - fail validation when dto is null")
    void validateDtoNullDto() {
        try {
            educationValidator.validateDto(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate dto - fail validation when DescriptionValidator fails (description is null)")
    void validateDtoDescriptionValidatorFails() {
        educationDto.setDescription(null);

        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DESCRIPTION_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(educationDto.getDescription(), ENTITY_NAME, DESCRIPTION_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        try {
            educationValidator.validateDto(educationDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DESCRIPTION_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

}
