package miwm.job4me.validators.entity.event;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.event.JobFair;
import miwm.job4me.validators.fields.DateAndTimeRangeValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.event.JobFairDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class JobFairValidatorTest {
    @Mock
    private StringFieldValidator stringFieldValidator;

    @Mock
    private DateAndTimeRangeValidator dateAndTimeRangeValidator;

    @InjectMocks
    private JobFairValidator jobFairValidator;

    private JobFair jobFair;
    private JobFairDto jobFairDto;

    private final int MIN_NAME_LENGTH = 1;
    private final int MAX_NAME_LENGTH = 100;
    private final int MIN_ADDRESS_LENGTH = 1;
    private final int MAX_ADDRESS_LENGTH = 200;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 1000;
    private final int MIN_DISPLAY_DESCRIPTION_LENGTH = 1;
    private final int MAX_DISPLAY_DESCRIPTION_LENGTH = 150;
    private final int MIN_PHOTO_LENGTH = 0;
    private final int MAX_PHOTO_LENGTH = 13000;
    private final String ENTITY_NAME = "JobFair";

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusDays(1);

        jobFair = JobFair.builder()
                .id(1L)
                .name("test")
                .address("test")
                .description("test")
                .displayDescription("test")
                .photo("test")
                .dateStart(now)
                .dateEnd(later)
                .build();

        jobFairDto = new JobFairDto();
        jobFairDto.setId(jobFair.getId());
        jobFairDto.setName(jobFair.getName());
        jobFairDto.setAddress(jobFair.getAddress());
        jobFairDto.setDescription(jobFair.getDescription());
        jobFairDto.setDisplayDescription(jobFair.getDisplayDescription());
        jobFairDto.setPhoto(jobFair.getPhoto());
        jobFairDto.setDateStart(jobFair.getDateStart());
        jobFairDto.setDateEnd(jobFair.getDateEnd());
    }

    @Test
    @DisplayName("test validate - pass validation when jobFair is valid")
    void validateValidJobFair() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getAddress(), ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getDisplayDescription(), ENTITY_NAME, "displayDescription", MIN_DISPLAY_DESCRIPTION_LENGTH, MAX_DISPLAY_DESCRIPTION_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getPhoto(), ENTITY_NAME, "photo", MIN_PHOTO_LENGTH, MAX_PHOTO_LENGTH);
        doNothing().when(dateAndTimeRangeValidator).validateRequiredDateRange(jobFair.getDateStart(), jobFair.getDateEnd(), ENTITY_NAME, "dateStart", "dateEnd");

        assertDoesNotThrow(() -> jobFairValidator.validate(jobFair));
    }

    @Test
    @DisplayName("test validate - fail validation when jobFair is null")
    void validateNullJobFair() {
        try {
            jobFairValidator.validate(null);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (Name is null)")
    void validateStringFieldValidatorFailsNameNull() {
        jobFair.setName(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("name", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        try {
            jobFairValidator.validate(jobFair);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("name", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (Address is null)")
    void validateStringFieldValidatorFailsAddressNull() {
        jobFair.setAddress(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("address", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getAddress(), ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);

        try {
            jobFairValidator.validate(jobFair);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("address", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (Description is null)")
    void validateStringFieldValidatorFailsDescriptionNull() {
        jobFair.setDescription(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getAddress(), ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("description", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        try {
            jobFairValidator.validate(jobFair);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("description", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (DisplayDescription is null)")
    void validateStringFieldValidatorFailsDisplayDescriptionNull() {
        jobFair.setDisplayDescription(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getAddress(), ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("displayDescription", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getDisplayDescription(), ENTITY_NAME, "displayDescription", MIN_DISPLAY_DESCRIPTION_LENGTH, MAX_DISPLAY_DESCRIPTION_LENGTH);

        try {
            jobFairValidator.validate(jobFair);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("displayDescription", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when StringFieldValidator fails (Photo is null)")
    void validateStringFieldValidatorFailsPhotoNull() {
        jobFair.setPhoto(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getAddress(), ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getDisplayDescription(), ENTITY_NAME, "displayDescription", MIN_DISPLAY_DESCRIPTION_LENGTH, MAX_DISPLAY_DESCRIPTION_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("photo", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getPhoto(), ENTITY_NAME, "photo", MIN_PHOTO_LENGTH, MAX_PHOTO_LENGTH);

        try {
            jobFairValidator.validate(jobFair);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("photo", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail validation when DateAndTimeRangeValidator fails (DateStart is null)")
    void validateDateAndTimeRangeValidatorFailsDateStartNull() {
        jobFair.setDateStart(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getAddress(), ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getDisplayDescription(), ENTITY_NAME, "displayDescription", MIN_DISPLAY_DESCRIPTION_LENGTH, MAX_DISPLAY_DESCRIPTION_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFair.getPhoto(), ENTITY_NAME, "photo", MIN_PHOTO_LENGTH, MAX_PHOTO_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNull("dateStart", ENTITY_NAME))).when(dateAndTimeRangeValidator).validateRequiredDateRange(jobFair.getDateStart(), jobFair.getDateEnd(), ENTITY_NAME, "dateStart", "dateEnd");

        try {
            jobFairValidator.validate(jobFair);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull("dateStart", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - pass validation when jobFairDto is valid")
    void validateValidJobFairDto() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getAddress(), ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getDisplayDescription(), ENTITY_NAME, "displayDescription", MIN_DISPLAY_DESCRIPTION_LENGTH, MAX_DISPLAY_DESCRIPTION_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getPhoto(), ENTITY_NAME, "photo", MIN_PHOTO_LENGTH, MAX_PHOTO_LENGTH);
        doNothing().when(dateAndTimeRangeValidator).validateRequiredDateRange(jobFairDto.getDateStart(), jobFairDto.getDateEnd(), ENTITY_NAME, "dateStart", "dateEnd");

        assertDoesNotThrow(() -> jobFairValidator.validateDto(jobFairDto));
    }

    @Test
    @DisplayName("test validateDto - fail validation when jobFairDto is null")
    void validateNullJobFairDto() {
        try {
            jobFairValidator.validateDto(null);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail validation when StringFieldValidator fails (Name is null)")
    void validateStringFieldValidatorFailsNameNullDto() {
        jobFairDto.setName(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("name", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);

        try {
            jobFairValidator.validateDto(jobFairDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("name", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail validation when StringFieldValidator fails (Address is null)")
    void validateStringFieldValidatorFailsAddressNullDto() {
        jobFairDto.setAddress(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("address", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getAddress(), ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);

        try {
            jobFairValidator.validateDto(jobFairDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("address", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail validation when StringFieldValidator fails (Description is null)")
    void validateStringFieldValidatorFailsDescriptionNullDto() {
        jobFairDto.setDescription(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getAddress(), ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("description", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        try {
            jobFairValidator.validateDto(jobFairDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("description", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail validation when StringFieldValidator fails (DisplayDescription is null)")
    void validateStringFieldValidatorFailsDisplayDescriptionNullDto() {
        jobFairDto.setDisplayDescription(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getAddress(), ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("displayDescription", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getDisplayDescription(), ENTITY_NAME, "displayDescription", MIN_DISPLAY_DESCRIPTION_LENGTH, MAX_DISPLAY_DESCRIPTION_LENGTH);

        try {
            jobFairValidator.validateDto(jobFairDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("displayDescription", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail validation when StringFieldValidator fails (Photo is null)")
    void validateStringFieldValidatorFailsPhotoNullDto() {
        jobFairDto.setPhoto(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getAddress(), ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getDisplayDescription(), ENTITY_NAME, "displayDescription", MIN_DISPLAY_DESCRIPTION_LENGTH, MAX_DISPLAY_DESCRIPTION_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("photo", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getPhoto(), ENTITY_NAME, "photo", MIN_PHOTO_LENGTH, MAX_PHOTO_LENGTH);

        try {
            jobFairValidator.validateDto(jobFairDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("photo", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail validation when DateAndTimeRangeValidator fails (DateStart is null)")
    void validateDateAndTimeRangeValidatorFailsDateStartNullDto() {
        jobFairDto.setDateStart(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getAddress(), ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getDisplayDescription(), ENTITY_NAME, "displayDescription", MIN_DISPLAY_DESCRIPTION_LENGTH, MAX_DISPLAY_DESCRIPTION_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobFairDto.getPhoto(), ENTITY_NAME, "photo", MIN_PHOTO_LENGTH, MAX_PHOTO_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNull("dateStart", ENTITY_NAME))).when(dateAndTimeRangeValidator).validateRequiredDateRange(jobFairDto.getDateStart(), jobFairDto.getDateEnd(), ENTITY_NAME, "dateStart", "dateEnd");

        try {
            jobFairValidator.validateDto(jobFairDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull("dateStart", ENTITY_NAME), e.getMessage());
        }
    }
}
