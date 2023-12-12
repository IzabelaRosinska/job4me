package miwm.job4me.validators.entity.offer.description;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.description.Requirement;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.RequirementDto;
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
class RequirementValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;
    @InjectMocks
    private RequirementValidator requirementValidator;
    private Requirement requirement;
    private RequirementDto requirementDto;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 250;
    private final String ENTITY_NAME = "Requirement";
    private final String DATA_FIELD_NAME = "description";

    @BeforeEach
    void setUp() {
        JobOffer jobOffer = JobOffer.builder().id(1L).build();

        requirement = Requirement.builder()
                .id(1L)
                .description("test")
                .jobOffer(jobOffer)
                .build();

        requirementDto = new RequirementDto();
        requirementDto.setId(requirement.getId());
        requirementDto.setDescription(requirement.getDescription());
    }

    @Test
    @DisplayName("Validate dto - pass validation when dto is valid")
    void validateDtoValidDto() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(requirementDto.getDescription(), ENTITY_NAME, DATA_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        assertDoesNotThrow(() -> requirementValidator.validateDto(requirementDto));
    }

    @Test
    @DisplayName("Validate dto - fail validation when dto is null")
    void validateDtoNullDto() {
        try {
            requirementValidator.validateDto(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate dto - fail validation when StringFieldValidator fails (description is null)")
    void validateDtoStringFieldValidatorFails() {
        requirementDto.setDescription(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(requirementDto.getDescription(), ENTITY_NAME, DATA_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        try {
            requirementValidator.validateDto(requirementDto);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - pass validation when Requirement is valid")
    void validateValidRequirement() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(requirement.getDescription(), ENTITY_NAME, DATA_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        assertDoesNotThrow(() -> requirementValidator.validate(requirement));
    }

    @Test
    @DisplayName("test validate - fail validation when Requirement is null")
    void validateNullRequirement() {
        try {
            requirementValidator.validate(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (description is null)")
    void validateStringFieldValidatorFails() {
        requirement.setDescription(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(requirement.getDescription(), ENTITY_NAME, DATA_FIELD_NAME, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        try {
            requirementValidator.validate(requirement);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty(DATA_FIELD_NAME, ENTITY_NAME), e.getMessage());
        }
    }

}
