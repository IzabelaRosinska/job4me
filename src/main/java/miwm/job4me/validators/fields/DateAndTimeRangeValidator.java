package miwm.job4me.validators.fields;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateAndTimeRangeValidator {
    public DateAndTimeRangeValidator() {
    }

    public void validateDateNotFromPast(LocalDateTime date, String entityName, String dateFieldName) {
        if (date == null) {
            throw new InvalidArgumentException(ExceptionMessages.notNull(entityName, dateFieldName));
        }

        if (date.isBefore(LocalDateTime.now())) {
            throw new InvalidArgumentException(ExceptionMessages.dateFromPast(entityName, dateFieldName));
        }
    }

    public void validateRequiredDateRange(LocalDateTime dateStart, LocalDateTime dateEnd, String entityName, String dateStartFieldName, String dateEndFieldName) {
        validateDateNotFromPast(dateStart, entityName, dateStartFieldName);

        if (dateEnd == null) {
            throw new InvalidArgumentException(ExceptionMessages.notNull(entityName, dateEndFieldName));
        }

        if (dateStart.isAfter(dateEnd)) {
            throw new InvalidArgumentException(ExceptionMessages.dateStartAfterDateEnd(entityName, dateStartFieldName, dateEndFieldName));
        }
    }

}
