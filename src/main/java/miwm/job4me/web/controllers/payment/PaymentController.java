package miwm.job4me.web.controllers.payment;

import miwm.job4me.services.payment.PaymentService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.web.model.payment.PaymentCheckout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private final PaymentService paymentService;
    private final OrganizerService organizerService;

    public PaymentController(PaymentService paymentService, OrganizerService organizerService) {
        this.paymentService = paymentService;
        this.organizerService = organizerService;
    }

    @GetMapping("organizer/payment/organizer/account/success")
    public String success() {
        Long organizerId = organizerService.getAuthOrganizer().getId();

        return "success" + organizerId;
    }

    @GetMapping("organizer/payment/organizer/account/cancel")
    public String cancel() {
        return "cancel";
    }

    @GetMapping("/payment")
    public ResponseEntity<PaymentCheckout> payForOrganizerAccount() {
        PaymentCheckout paymentCheckout = paymentService.payForOrganizerAccount();

        return new ResponseEntity<>(paymentCheckout, HttpStatus.OK);
    }

}
