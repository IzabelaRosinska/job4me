package miwm.job4me.validators.fields;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class ListValidatorTest {
    @InjectMocks
    private ListValidator listValidator;
    private final int MIN_LIST_SIZE = 1;
    private final int MAX_LIST_SIZE = 10;
    private final int MAX_ELEM_LENGTH = 10;
    private final String TEST_FIELD = "testField";
    private final String TEST_ENTITY = "Test";

    @Test
    @DisplayName("validateListSizeAndElemLength - pass validation")
    public void validateListSizeAndElemLengthValid() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("test");

        try {
            listValidator.validateListSizeAndElemLength(list, TEST_FIELD, TEST_ENTITY, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
        } catch (InvalidArgumentException e) {
            fail();
        }
    }

    @Test
    @DisplayName("validateListSizeAndElemLength - pass validation when list is null")
    public void validateListSizeAndElemLengthValidNullList() {
        ArrayList<String> list = null;

        try {
            listValidator.validateListSizeAndElemLength(list, TEST_FIELD, TEST_ENTITY, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
        } catch (InvalidArgumentException e) {
            fail();
        }
    }

    @Test
    @DisplayName("validateListSizeAndElemLength - fail validation when list is too long")
    public void validateListSizeAndElemLengthInvalidTooLongList() {
        String expectedMessage = ExceptionMessages.listTooLong(TEST_ENTITY, TEST_FIELD, MAX_LIST_SIZE);
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < MAX_LIST_SIZE + 1; i++) {
            list.add("test");
        }

        try {
            listValidator.validateListSizeAndElemLength(list, TEST_FIELD, TEST_ENTITY, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("validateListSizeAndElemLength - fail validation when list element is too long")
    public void validateListSizeAndElemLengthInvalidTooLongListElement() {
        String expectedMessage = ExceptionMessages.textTooLong(TEST_ENTITY, TEST_FIELD, MAX_ELEM_LENGTH);
        ArrayList<String> list = new ArrayList<String>();
        list.add("a".repeat(MAX_ELEM_LENGTH + 1));

        try {
            listValidator.validateListSizeAndElemLength(list, TEST_FIELD, TEST_ENTITY, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("validateListSizeAndElemLength - fail validation when list element is null or empty")
    public void validateListSizeAndElemLengthInvalidNullOrEmptyListElement() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(TEST_ENTITY, TEST_FIELD + " list element");
        ArrayList<String> list = new ArrayList<String>();
        list.add(null);

        try {
            listValidator.validateListSizeAndElemLength(list, TEST_FIELD, TEST_ENTITY, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        list.set(0, "");
        try {
            listValidator.validateListSizeAndElemLength(list, TEST_FIELD, TEST_ENTITY, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("validateRequiredListMinMaxSize - pass validation")
    public void validateRequiredListMinMaxSizeValid() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("test");

        try {
            listValidator.validateRequiredListMinMaxSize(list, TEST_FIELD, TEST_ENTITY, MIN_LIST_SIZE, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
        } catch (InvalidArgumentException e) {
            fail();
        }
    }

    @Test
    @DisplayName("validateRequiredListMinMaxSize - fail validation when list is null or empty")
    public void validateRequiredListMinMaxSizeInvalidNullList() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(TEST_ENTITY, TEST_FIELD);
        ArrayList<String> list = null;

        try {
            listValidator.validateRequiredListMinMaxSize(list, TEST_FIELD, TEST_ENTITY, MIN_LIST_SIZE, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        list = new ArrayList<String>();
        try {
            listValidator.validateRequiredListMinMaxSize(list, TEST_FIELD, TEST_ENTITY, MIN_LIST_SIZE, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("validateRequiredListMinMaxSize - fail validation when list is too long")
    public void validateRequiredListMinMaxSizeInvalidTooLongList() {
        String expectedMessage = ExceptionMessages.listTooLong(TEST_ENTITY, TEST_FIELD, MAX_LIST_SIZE);
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < MAX_LIST_SIZE + 1; i++) {
            list.add("test");
        }

        try {
            listValidator.validateRequiredListMinMaxSize(list, TEST_FIELD, TEST_ENTITY, MIN_LIST_SIZE, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("validateRequiredListMinMaxSize - fail validation when list is too short")
    public void validateRequiredListMinMaxSizeInvalidTooShortList() {
        int minSize = 2;
        String expectedMessage = ExceptionMessages.lengthOutOfRange(TEST_ENTITY, TEST_FIELD, minSize, MAX_LIST_SIZE);
        ArrayList<String> list = new ArrayList<String>();
        list.add("test");

        try {
            listValidator.validateRequiredListMinMaxSize(list, TEST_FIELD, TEST_ENTITY, minSize, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("validateRequiredListMinMaxSize - fail validation when list element is too long")
    public void validateRequiredListMinMaxSizeInvalidTooLongListElement() {
        String expectedMessage = ExceptionMessages.textTooLong(TEST_ENTITY, TEST_FIELD, MAX_ELEM_LENGTH);
        ArrayList<String> list = new ArrayList<String>();
        list.add("a".repeat(MAX_ELEM_LENGTH + 1));

        try {
            listValidator.validateRequiredListMinMaxSize(list, TEST_FIELD, TEST_ENTITY, MIN_LIST_SIZE, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("validateRequiredListMinMaxSize - fail validation when list element is null or empty")
    public void validateRequiredListMinMaxSizeInvalidNullOrEmptyListElement() {
        String expectedMessage = ExceptionMessages.notNullNotEmpty(TEST_ENTITY, TEST_FIELD + " list element");
        ArrayList<String> list = new ArrayList<String>();
        list.add(null);

        try {
            listValidator.validateRequiredListMinMaxSize(list, TEST_FIELD, TEST_ENTITY, MIN_LIST_SIZE, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        list.set(0, "");
        try {
            listValidator.validateRequiredListMinMaxSize(list, TEST_FIELD, TEST_ENTITY, MIN_LIST_SIZE, MAX_LIST_SIZE, MAX_ELEM_LENGTH);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
