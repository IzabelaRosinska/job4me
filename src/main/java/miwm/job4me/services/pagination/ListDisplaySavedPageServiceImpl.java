package miwm.job4me.services.pagination;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.validators.pagination.PaginationValidator;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListDisplaySavedPageServiceImpl implements PageService<ListDisplaySavedDto> {
    private final PaginationValidator paginationValidator;

    public ListDisplaySavedPageServiceImpl(PaginationValidator paginationValidator) {
        this.paginationValidator = paginationValidator;
    }

    @Override
    public Page<ListDisplaySavedDto> createPage(List<ListDisplaySavedDto> list, int page, int size) {
        paginationValidator.validatePagination(page, size);

        int start = page * size;
        int end = Math.min((start + size), list.size());
        List<ListDisplaySavedDto> subList = list.subList(start, end);
        PageRequest pageRequest = PageRequest.of(page, size);

        return new PageImpl<>(subList, pageRequest, list.size());
    }

    @Override
    public Page<ListDisplaySavedDto> createPageGivenSublist(List<ListDisplaySavedDto> sublist, int page, int size, int totalSize) {
        paginationValidator.validatePagination(page, size);

        if (sublist.size() > totalSize || sublist.size() > size) {
            throw new InvalidArgumentException(sublistSizeInvalidMessage(sublist.size(), size, totalSize));
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        return new PageImpl<>(sublist, pageRequest, totalSize);
    }

    public static String sublistSizeInvalidMessage(int sublistSize, int size, int totalSize) {
        return String.format("Sublist size %d cannot be greater than size %d or greater than total size %d", sublistSize, size, totalSize);
    }

}
