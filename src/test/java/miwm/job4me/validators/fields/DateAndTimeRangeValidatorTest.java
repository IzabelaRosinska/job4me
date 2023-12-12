package miwm.job4me.validators.fields;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DateAndTimeRangeValidatorTest {
    @InjectMocks
    private DateAndTimeRangeValidator dateAndTimeRangeValidator;
    private final String ENTITY_NAME = "entityName";

    @Test
    @DisplayName("test validateDateNotFromPast - date is from future")
    void testValidateDateNotFromPastPassDateIsFromFuture() {
        LocalDateTime date = LocalDateTime.now().plusDays(1);

        assertDoesNotThrow(() -> dateAndTimeRangeValidator.validateDateNotFromPast(date, ENTITY_NAME, "dateFieldName"));
    }

    @Test
    @DisplayName("test validateDateNotFromPast - date is null")
    void testValidateDateNotFromPastPassDateIsNull() {
        try {
            dateAndTimeRangeValidator.validateDateNotFromPast(null, ENTITY_NAME, "dateFieldName");
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, "dateFieldName"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDateNotFromPast - date is from past")
    void testValidateDateNotFromPastPassDateIsFromPast() {
        LocalDateTime date = LocalDateTime.now().minusDays(1);

        try {
            dateAndTimeRangeValidator.validateDateNotFromPast(date, ENTITY_NAME, "dateFieldName");
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.dateFromPast(ENTITY_NAME, "dateFieldName"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateRequiredDateRange - dateStart is from future, dateEnd is from future")
    void testValidateRequiredDateRangePassDateStartIsFromFutureDateEndIsFromFuture() {
        LocalDateTime dateStart = LocalDateTime.now().plusDays(1);
        LocalDateTime dateEnd = LocalDateTime.now().plusDays(2);

        assertDoesNotThrow(() -> dateAndTimeRangeValidator.validateRequiredDateRange(dateStart, dateEnd, ENTITY_NAME, "dateStartFieldName", "dateEndFieldName"));
    }

    @Test
    @DisplayName("test validateRequiredDateRange - dateStart is null, dateEnd is from future")
    void testValidateRequiredDateRangeFailDateStartIsNullDateEndIsFromFuture() {
        LocalDateTime dateEnd = LocalDateTime.now().plusDays(2);

        try {
            dateAndTimeRangeValidator.validateRequiredDateRange(null, dateEnd, ENTITY_NAME, "dateStartFieldName", "dateEndFieldName");
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, "dateStartFieldName"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateRequiredDateRange - dateStart is from past, dateEnd is from future")
    void testValidateRequiredDateRangeFailDateStartIsFromPastDateEndIsFromFuture() {
        LocalDateTime dateStart = LocalDateTime.now().minusDays(1);
        LocalDateTime dateEnd = LocalDateTime.now().plusDays(2);

        try {
            dateAndTimeRangeValidator.validateRequiredDateRange(dateStart, dateEnd, ENTITY_NAME, "dateStartFieldName", "dateEndFieldName");
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.dateFromPast(ENTITY_NAME, "dateStartFieldName"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateRequiredDateRange - dateStart is from future, dateEnd is null")
    void testValidateRequiredDateRangeFailDateStartIsFromFutureDateEndIsNull() {
        LocalDateTime dateStart = LocalDateTime.now().plusDays(1);

        try {
            dateAndTimeRangeValidator.validateRequiredDateRange(dateStart, null, ENTITY_NAME, "dateStartFieldName", "dateEndFieldName");
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, "dateEndFieldName"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateRequiredDateRange - dateStart is from future, dateEnd is from past")
    void testValidateRequiredDateRangeFailDateStartIsFromFutureDateEndIsFromPast() {
        LocalDateTime dateStart = LocalDateTime.now().plusDays(1);
        LocalDateTime dateEnd = LocalDateTime.now().minusDays(1);

        try {
            dateAndTimeRangeValidator.validateRequiredDateRange(dateStart, dateEnd, ENTITY_NAME, "dateStartFieldName", "dateEndFieldName");
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.dateStartAfterDateEnd(ENTITY_NAME, "dateStartFieldName", "dateEndFieldName"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateRequiredDateRange - dateStart is from future, dateEnd is from future, dateStart is after dateEnd")
    void testValidateRequiredDateRangeFailDateStartIsFromFutureDateEndIsFromFutureDateStartIsAfterDateEnd() {
        LocalDateTime dateStart = LocalDateTime.now().plusDays(2);
        LocalDateTime dateEnd = LocalDateTime.now().plusDays(1);

        try {
            dateAndTimeRangeValidator.validateRequiredDateRange(dateStart, dateEnd, ENTITY_NAME, "dateStartFieldName", "dateEndFieldName");
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.dateStartAfterDateEnd(ENTITY_NAME, "dateStartFieldName", "dateEndFieldName"), e.getMessage());
        }
    }

}
