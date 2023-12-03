package miwm.job4me.services.payment;

import miwm.job4me.web.model.event.JobFairDto;
import miwm.job4me.web.model.payment.PaymentCheckout;

public interface PaymentService {

    PaymentCheckout coordinateJobFairPayment(JobFairDto jobFairDto);

    PaymentCheckout createJobFairPayment(String redirectData);

    String createPaymentSession(Long productQuantity, Long productPrice, String productName, String successUrl, String cancelUrl, String productImageUrl, String productDescription, String customerEmail);

}
