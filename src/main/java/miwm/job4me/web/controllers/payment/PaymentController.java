package miwm.job4me.web.controllers.payment;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.payment.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("job-fairs/payment/webhook")
    @Operation(summary = "Handle Stripe webhook", description = "Handle Stripe webhook")
    public ResponseEntity<String> webhook(@RequestBody String payload,
                                          @RequestHeader("Stripe-Signature") String sigHeader) {
        paymentService.handleCheckoutPayment(payload, sigHeader);

        return new ResponseEntity<>("Webhook received successfully", HttpStatus.OK);
    }

}
