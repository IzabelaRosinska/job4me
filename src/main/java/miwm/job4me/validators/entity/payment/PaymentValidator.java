package miwm.job4me.validators.entity.payment;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.payment.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator {

    private final String ENTITY_NAME = "Payment";

    public void validate(Payment payment) {
        if (payment == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        if (payment.getSessionId() == null || payment.getSessionId().isEmpty()) {
            throw new InvalidArgumentException(ExceptionMessages.notNull(ENTITY_NAME, "sessionId"));
        }
    }

}
