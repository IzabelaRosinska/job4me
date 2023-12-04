package miwm.job4me.services.payment;

import com.stripe.model.checkout.Session;
import miwm.job4me.model.payment.Payment;

public interface PaymentService {

    Payment save(Payment payment);

    void updatePaymentStatus(String sessionId);

    Payment getPaymentBySessionId(String sessionId);

    void handleCheckoutPayment(String payload, String sigHeader);

    Session createJobFairPayment(String customerEmail);

    Session createPaymentSession(Long productQuantity, Long productPrice, String productName, String successUrl, String cancelUrl, String productImageUrl, String productDescription, String customerEmail);

}
