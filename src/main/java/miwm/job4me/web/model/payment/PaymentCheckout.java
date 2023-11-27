package miwm.job4me.web.model.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCheckout {
    @Schema(description = "Url to redirect to Stripe Checkout", example = "https://checkout.stripe.com/pay/")
    private String url;
}
