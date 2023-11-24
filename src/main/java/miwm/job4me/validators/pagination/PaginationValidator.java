package miwm.job4me.validators.pagination;

import miwm.job4me.exceptions.InvalidArgumentException;
import org.springframework.stereotype.Component;

@Component
public class PaginationValidator {
    public void validatePagination(int page, int size) {
        if (page < 0) {
            throw new InvalidArgumentException(invalidPageNumber("page"));
        }

        if (size < 0) {
            throw new InvalidArgumentException(invalidPageNumber("size"));
        }
    }

    public static String invalidPageNumber(String fieldName) {
        return String.format("Page %s must be greater than or equal to 0", fieldName);
    }
}
