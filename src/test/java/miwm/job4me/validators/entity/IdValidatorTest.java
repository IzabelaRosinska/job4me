package miwm.job4me.validators.entity;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IdValidatorTest {
    @InjectMocks
    private IdValidator idValidator;
    private final String ENTITY_NAME = "Test";

    @Test
    @DisplayName("Validate id - pass validation when valid id")
    public void validateLongIdValidId() {
        assertDoesNotThrow(() -> idValidator.validateLongId(1L, ENTITY_NAME));
    }

    @Test
    @DisplayName("Validate id - fail validation when invalid id (null)")
    public void validateLongIdNullId() {
        try {
            idValidator.validateLongId(null, ENTITY_NAME);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

}
