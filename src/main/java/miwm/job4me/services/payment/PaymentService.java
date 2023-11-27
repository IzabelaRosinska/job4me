package miwm.job4me.services.payment;

public interface PaymentService {
    String payForOrganizerAccount();

    String createPaymentSession(Long quantity, Long price, String itemName, String successUrl, String cancelUrl);
}
