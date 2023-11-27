package miwm.job4me.services.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import miwm.job4me.exceptions.PaymentException;
import miwm.job4me.web.model.payment.PaymentCheckout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static miwm.job4me.messages.AppMessages.BACKEND_HOST_AZURE;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String recommendationApiKey;

    @Override
    public PaymentCheckout payForOrganizerAccount() {
        PaymentCheckout paymentCheckout = new PaymentCheckout();
        String url = createPaymentSession(1L, 200L, "Organizer account", BACKEND_HOST_AZURE + "/payment/organizer/account/success", BACKEND_HOST_AZURE + "/payment/organizer/account/cancel", "https://i.ibb.co/KXsSS6q/job4-Me-2.png");
        paymentCheckout.setUrl(url);

        return paymentCheckout;
    }

    @Override
    public String createPaymentSession(Long quantity, Long price, String itemName, String successUrl, String cancelUrl, String imageUrl) {
        Stripe.apiKey = recommendationApiKey;

        try {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.P24)
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.BLIK)
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(quantity)
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setCurrency("pln")
                                                            .setUnitAmount(price)
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                            .setName(itemName)
                                                                            .addImage(imageUrl)
                                                                            .build())
                                                            .build())
                                            .build())
                            .setSuccessUrl(successUrl)
                            .setCancelUrl(cancelUrl)
                            .build();

            Session session = Session.create(params);

            return session.getUrl();
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            throw new PaymentException(e.getMessage(), e);
        }
    }

}
