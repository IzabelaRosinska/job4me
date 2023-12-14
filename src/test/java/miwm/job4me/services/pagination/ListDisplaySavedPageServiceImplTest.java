package miwm.job4me.services.pagination;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.validators.pagination.PaginationValidator;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ListDisplaySavedPageServiceImplTest {
    @Mock
    private PaginationValidator paginationValidator;
    @InjectMocks
    private ListDisplaySavedPageServiceImpl listDisplaySavedPageService;

    private ListDisplaySavedDto listDisplaySavedDto1;
    private ListDisplaySavedDto listDisplaySavedDto2;
    private ListDisplaySavedDto listDisplaySavedDto3;

    @BeforeEach
    void setUp() {
        listDisplaySavedDto1 = new ListDisplaySavedDto();
        listDisplaySavedDto2 = new ListDisplaySavedDto();
        listDisplaySavedDto3 = new ListDisplaySavedDto();
    }

    @Test
    @DisplayName("test createPage - returns page with correct size when all elements fit on page")
    void testCreatePageReturnsPageWithCorrectSizeWhenPaginationIsValid() {
        // given
        List<ListDisplaySavedDto> list = List.of(listDisplaySavedDto1, listDisplaySavedDto2, listDisplaySavedDto3);
        int page = 0;
        int size = 3;

        // when
        doNothing().when(paginationValidator).validatePagination(page, size);
        Page<ListDisplaySavedDto> result = listDisplaySavedPageService.createPage(list, page, size);

        // then
        assertEquals(3, result.getContent().size());
    }

    @Test
    @DisplayName("test createPage - returns page with correct size when not all elements fit on page")
    void testCreatePageReturnsPageWithCorrectSizeWhenNotAllElementsFitOnPage() {
        // given
        List<ListDisplaySavedDto> list = List.of(listDisplaySavedDto1, listDisplaySavedDto2, listDisplaySavedDto3);
        int page = 0;
        int size = 2;

        // when
        doNothing().when(paginationValidator).validatePagination(page, size);
        Page<ListDisplaySavedDto> result = listDisplaySavedPageService.createPage(list, page, size);

        // then
        assertEquals(2, result.getContent().size());
    }

    @Test
    @DisplayName("test createPage - returns page with correct size when not all elements fit on page")
    void testCreatePageReturnsNextPageWithCorrectSizeWhenNotAllElementsFitOnPage() {
        // given
        List<ListDisplaySavedDto> list = List.of(listDisplaySavedDto1, listDisplaySavedDto2, listDisplaySavedDto3);
        int page = 1;
        int size = 2;

        // when
        doNothing().when(paginationValidator).validatePagination(page, size);
        Page<ListDisplaySavedDto> result = listDisplaySavedPageService.createPage(list, page, size);

        // then
        assertEquals(1, result.getContent().size());
    }

    @Test
    @DisplayName("test createPage - fails when PaginationValidator fails (size < 0)")
    void testCreatePageFailsWhenPaginationValidatorFails() {
        // given
        String exceptionMessage = PaginationValidator.invalidSizeNumber();
        List<ListDisplaySavedDto> list = List.of(listDisplaySavedDto1, listDisplaySavedDto2, listDisplaySavedDto3);
        int page = 0;
        int size = -1;

        // when
        doThrow(new InvalidArgumentException(exceptionMessage)).when(paginationValidator).validatePagination(page, size);

        try {
            listDisplaySavedPageService.createPage(list, page, size);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            // then
            assertEquals(exceptionMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("test createPageGivenSublist - returns page with correct size when all elements fit on page")
    void testCreatePageGivenSublistReturnsPageWithCorrectSizeWhenPaginationIsValid() {
        // given
        List<ListDisplaySavedDto> list = List.of(listDisplaySavedDto1, listDisplaySavedDto2, listDisplaySavedDto3);
        int page = 0;
        int size = 3;
        int totalSize = 3;

        // when
        doNothing().when(paginationValidator).validatePagination(page, size);
        Page<ListDisplaySavedDto> result = listDisplaySavedPageService.createPageGivenSublist(list, page, size, totalSize);

        // then
        assertEquals(3, result.getContent().size());
    }

    @Test
    @DisplayName("test createPageGivenSublist - fail when sublist size is greater than total size")
    void testCreatePageGivenSublistReturnsPageWithCorrectSizeWhenNotAllElementsFitOnPage() {
        // given
        int page = 0;
        int size = 3;
        int totalSize = 1;
        List<ListDisplaySavedDto> list = List.of(listDisplaySavedDto1, listDisplaySavedDto2);
        String exceptionMessage = ListDisplaySavedPageServiceImpl.sublistSizeInvalidMessage(list.size(), size, totalSize);
        // when
        doNothing().when(paginationValidator).validatePagination(page, size);

        try {
            listDisplaySavedPageService.createPageGivenSublist(list, page, size, totalSize);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            // then
            assertEquals(exceptionMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("test createPageGivenSublist - fail when sublist size is greater than size")
    void testCreatePageGivenSublistReturnsPageWithCorrectSizeWhenNotAllElementsFitOnPage2() {
        // given
        int page = 0;
        int size = 3;
        int totalSize = 1;
        List<ListDisplaySavedDto> list = List.of(listDisplaySavedDto1, listDisplaySavedDto2);
        String exceptionMessage = ListDisplaySavedPageServiceImpl.sublistSizeInvalidMessage(list.size(), size, totalSize);
        // when
        doNothing().when(paginationValidator).validatePagination(page, size);

        try {
            listDisplaySavedPageService.createPageGivenSublist(list, page, size, totalSize);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            // then
            assertEquals(exceptionMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("test createPageGivenSublist - fails when PaginationValidator fails (size < 0)")
    void testCreatePageGivenSublistFailsWhenPaginationValidatorFails() {
        // given
        String exceptionMessage = PaginationValidator.invalidSizeNumber();
        List<ListDisplaySavedDto> list = List.of(listDisplaySavedDto1, listDisplaySavedDto2, listDisplaySavedDto3);
        int page = 0;
        int size = -1;
        int totalSize = 3;

        // when
        doThrow(new InvalidArgumentException(exceptionMessage)).when(paginationValidator).validatePagination(page, size);

        try {
            listDisplaySavedPageService.createPageGivenSublist(list, page, size, totalSize);
            fail("Should throw InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            // then
            assertEquals(exceptionMessage, e.getMessage());
        }
    }

}
