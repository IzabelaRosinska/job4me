package miwm.job4me.services.payment;

import miwm.job4me.web.model.payment.PaymentCheckout;

public interface PaymentService {
    PaymentCheckout payForOrganizerAccount();

    String createPaymentSession(Long quantity, Long price, String itemName, String successUrl, String cancelUrl, String imageUrl);
}
