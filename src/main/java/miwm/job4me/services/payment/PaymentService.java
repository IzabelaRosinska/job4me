package miwm.job4me.services.payment;

import com.stripe.model.checkout.Session;
import miwm.job4me.web.model.event.JobFairDto;
import miwm.job4me.web.model.payment.PaymentCheckout;

public interface PaymentService {

    PaymentCheckout coordinateJobFairPayment(JobFairDto jobFairDto);

    void handleJobFairPaymentSuccess(String paymentRedirectToken);

    void handleJobFairPaymentCancel(String paymentRedirectToken);

    Session createJobFairPayment(String redirectData);

    Session createPaymentSession(Long productQuantity, Long productPrice, String productName, String successUrl, String cancelUrl, String productImageUrl, String productDescription, String customerEmail);

}
