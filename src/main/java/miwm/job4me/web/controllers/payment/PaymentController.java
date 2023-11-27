package miwm.job4me.web.controllers.payment;

import miwm.job4me.services.payment.PaymentService;
import miwm.job4me.web.model.payment.PaymentCheckout;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payment/organizer-account/success")
    public String success() {
        return "success";
    }

    @GetMapping("/payment/organizer-account/cancel")
    public String cancel() {
        return "cancel";
    }

    @GetMapping("/payment")
    public ResponseEntity<PaymentCheckout> payForOrganizerAccount() {
        PaymentCheckout paymentCheckout = new PaymentCheckout();
        paymentCheckout.setUrl(paymentService.payForOrganizerAccount());
        System.out.println(paymentCheckout.getUrl());

        return ResponseEntity.ok(paymentCheckout);
    }

}
