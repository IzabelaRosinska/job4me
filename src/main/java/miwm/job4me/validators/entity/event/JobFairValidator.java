package miwm.job4me.validators.entity.event;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.event.JobFair;
import miwm.job4me.validators.fields.DateAndTimeRangeValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.event.JobFairDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JobFairValidator {
    private final StringFieldValidator stringFieldValidator;
    private final DateAndTimeRangeValidator dateAndTimeRangeValidator;
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

    public JobFairValidator(StringFieldValidator stringFieldValidator, DateAndTimeRangeValidator dateAndTimeRangeValidator) {
        this.stringFieldValidator = stringFieldValidator;
        this.dateAndTimeRangeValidator = dateAndTimeRangeValidator;
    }

    public void validate(JobFair jobFair) {
        if (jobFair == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        validateJobFairFields(jobFair.getName(), jobFair.getAddress(), jobFair.getDescription(), jobFair.getDisplayDescription(), jobFair.getPhoto(), jobFair.getDateStart(), jobFair.getDateEnd());
    }

    public void validateDto(JobFairDto jobFair) {
        if (jobFair == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        validateJobFairFields(jobFair.getName(), jobFair.getAddress(), jobFair.getDescription(), jobFair.getDisplayDescription(), jobFair.getPhoto(), jobFair.getDateStart(), jobFair.getDateEnd());
    }

    private void validateJobFairFields(String name, String address, String description, String displayDescription, String photo, LocalDateTime dateStart, LocalDateTime dateEnd) {
        stringFieldValidator.validateClassicStringRestrictedField(name, ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        stringFieldValidator.validateClassicStringRestrictedField(address, ENTITY_NAME, "address", MIN_ADDRESS_LENGTH, MAX_ADDRESS_LENGTH);
        stringFieldValidator.validateClassicStringRestrictedField(description, ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        stringFieldValidator.validateClassicStringRestrictedField(displayDescription, ENTITY_NAME, "displayDescription", MIN_DISPLAY_DESCRIPTION_LENGTH, MAX_DISPLAY_DESCRIPTION_LENGTH);
        stringFieldValidator.validateClassicStringRestrictedField(photo, ENTITY_NAME, "photo", MIN_PHOTO_LENGTH, MAX_PHOTO_LENGTH);
        dateAndTimeRangeValidator.validateRequiredDateRange(dateStart, dateEnd, ENTITY_NAME, "dateStart", "dateEnd");
    }
}
