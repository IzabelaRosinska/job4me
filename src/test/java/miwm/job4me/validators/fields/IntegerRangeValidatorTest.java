package miwm.job4me.validators.fields;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IntegerRangeValidatorTest {
    @InjectMocks
    private IntegerRangeValidator integerRangeValidator;

    private final String ENTITY_NAME = "Test";

    @Test
    @DisplayName("test validateSalaryRange - pass validation when valid range (salaryTo > salaryFrom)")
    public void validatePassValidSalaryRange() {
        assertDoesNotThrow(() -> integerRangeValidator.validateSalaryRange(1, 2, ENTITY_NAME));
    }

    @Test
    @DisplayName("test validateSalaryRange - pass validation when valid range (salaryTo null)")
    public void validatePassValidSalaryRangeSalaryToNull() {
        assertDoesNotThrow(() -> integerRangeValidator.validateSalaryRange(1, null, ENTITY_NAME));
    }

    @Test
    @DisplayName("test validateSalaryRange - fail validation when invalid range (salaryTo < salaryFrom)")
    public void validateFailInvalidSalaryRange() {
        try {
            integerRangeValidator.validateSalaryRange(2, 1, ENTITY_NAME);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.invalidRange(ENTITY_NAME, "salaryFrom", "salaryTo"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateSalaryRange - fail validation when invalid range (salaryFrom < 0)")
    public void validateFailInvalidSalaryRangeSalaryFromNegative() {
        try {
            integerRangeValidator.validateSalaryRange(-1, 1, ENTITY_NAME);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.mustBePositive(ENTITY_NAME, "salaryFrom"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateSalaryRange - fail validation when invalid range (salaryTo < 0)")
    public void validateFailInvalidSalaryRangeSalaryToNegative() {
        try {
            integerRangeValidator.validateSalaryRange(1, -1, ENTITY_NAME);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.mustBePositive(ENTITY_NAME, "salaryTo"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateSalaryRange - fail validation when invalid range (salaryFrom null)")
    public void validateFailInvalidSalaryRangeSalaryFromNull() {
        try {
            integerRangeValidator.validateSalaryRange(null, 1, ENTITY_NAME);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, "salaryFrom"), e.getMessage());
        }
    }

}
