package miwm.job4me.services.payment;

import miwm.job4me.web.model.payment.PaymentCheckout;

public interface PaymentService {
    PaymentCheckout payForJobFair();

    String createPaymentSession(Long productQuantity, Long productPrice, String productName, String successUrl, String cancelUrl, String productImageUrl, String productDescription, String customerEmail);

}
