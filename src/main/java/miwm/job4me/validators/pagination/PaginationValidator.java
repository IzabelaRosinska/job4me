package miwm.job4me.validators.pagination;

import miwm.job4me.exceptions.InvalidArgumentException;
import org.springframework.stereotype.Component;

@Component
public class PaginationValidator {
    public void validatePagination(int page, int size) {
        if (page < 0) {
            throw new InvalidArgumentException(invalidPageNumber());
        }

        if (size <= 0) {
            throw new InvalidArgumentException(invalidSizeNumber());
        }
    }

    public static String invalidPageNumber() {
        return "Page must be greater than or equal to 0";
    }

    public static String invalidSizeNumber() {
        return "Size must be greater than 0";
    }
}
