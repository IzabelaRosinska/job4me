package miwm.job4me.web.controllers.payment;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.payment.PaymentService;
import miwm.job4me.web.model.event.JobFairDto;
import miwm.job4me.web.model.payment.PaymentCheckout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payment/organizer/account/success")
    public String success() {
        return "success";
    }

    @GetMapping("/payment/organizer/account/cancel")
    public String cancel() {
        return "cancel";
    }

    @PostMapping("/job-fairs/payment")
    @Operation(summary = "Create JobFair and payment session", description = "Create JobFair and payment session")
    public ResponseEntity<PaymentCheckout> payForOrganizerAccount(@RequestBody JobFairDto jobFairDto) {
        PaymentCheckout paymentCheckout = paymentService.coordinateJobFairPayment(jobFairDto);

        return new ResponseEntity<>(paymentCheckout, HttpStatus.OK);
    }

}
