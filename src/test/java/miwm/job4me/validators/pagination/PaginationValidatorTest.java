package miwm.job4me.validators.pagination;

import miwm.job4me.exceptions.InvalidArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaginationValidatorTest {
    @InjectMocks
    private PaginationValidator paginationValidator;

    @Test
    @DisplayName("test validatePagination - pass when page (>= 0) and size (>0) are valid")
    void testValidatePagination() {
        assertDoesNotThrow(() -> paginationValidator.validatePagination(0, 1));
    }

    @Test
    @DisplayName("test validatePagination - fail when page (< 0) is invalid")
    void testValidatePaginationFailWhenPageIsInvalid() {
        try {
            paginationValidator.validatePagination(-1, 1);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(PaginationValidator.invalidPageNumber(), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validatePagination - fail when size (<= 0) is invalid")
    void testValidatePaginationFailWhenSizeIsInvalid() {
        try {
            paginationValidator.validatePagination(0, 0);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(PaginationValidator.invalidSizeNumber(), e.getMessage());
        }
    }

}
