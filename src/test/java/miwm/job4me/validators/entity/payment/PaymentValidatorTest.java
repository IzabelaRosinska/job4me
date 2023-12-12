package miwm.job4me.validators.entity.payment;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.event.JobFair;
import miwm.job4me.model.payment.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentValidatorTest {
    @InjectMocks
    private PaymentValidator paymentValidator;

    private Payment payment;
    private final String ENTITY_NAME = "Payment";

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        JobFair jobFair = JobFair.builder()
                .id(1L)
                .build();

        payment = Payment.builder()
                .id(1L)
                .sessionId("sessionId")
                .isPaid(false)
                .creationTimestamp(now)
                .jobFair(jobFair)
                .build();
    }

    @Test
    @DisplayName("test validate - pass when payment is valid")
    void testValidate() {
        assertDoesNotThrow(() -> paymentValidator.validate(payment));
    }

    @Test
    @DisplayName("test validate - throw exception when payment is null")
    void testValidateFailWhenPaymentIsNull() {
        try {
            paymentValidator.validate(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - throw exception when sessionId is null")
    void testValidateFailWhenSessionIdIsNull() {
        payment.setSessionId(null);

        try {
            paymentValidator.validate(payment);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, "sessionId"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - throw exception when sessionId is empty")
    void testValidateFailWhenSessionIdIsEmpty() {
        payment.setSessionId("");

        try {
            paymentValidator.validate(payment);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, "sessionId"), e.getMessage());
        }
    }


    @Test
    @DisplayName("test validate - throw exception when isPaid is null")
    void testValidateFailWhenIsPaidIsNull() {
        payment.setIsPaid(null);

        try {
            paymentValidator.validate(payment);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, "isPaid"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - throw exception when creationTimestamp is null")
    void testValidateFailWhenCreationTimestampIsNull() {
        payment.setCreationTimestamp(null);

        try {
            paymentValidator.validate(payment);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, "creationTimestamp"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - throw exception when jobFair is null")
    void testValidateFailWhenJobFairIsNull() {
        payment.setJobFair(null);

        try {
            paymentValidator.validate(payment);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, "jobFair"), e.getMessage());
        }
    }

}
