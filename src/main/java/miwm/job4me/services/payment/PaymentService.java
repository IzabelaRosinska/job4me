package miwm.job4me.services.payment;

import com.stripe.model.checkout.Session;
import miwm.job4me.model.payment.Payment;
import miwm.job4me.web.model.event.JobFairDto;
import miwm.job4me.web.model.payment.PaymentCheckout;

public interface PaymentService {

    Payment save(Payment payment);

    void updatePaymentStatus(String sessionId);

    Payment getPaymentBySessionId(String sessionId);

    PaymentCheckout coordinateJobFairPayment(JobFairDto jobFairDto);

    void handleCheckoutPayment(String payload, String sigHeader);

    Session createJobFairPayment();

    Session createPaymentSession(Long productQuantity, Long productPrice, String productName, String successUrl, String cancelUrl, String productImageUrl, String productDescription, String customerEmail);

}
