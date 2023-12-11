package miwm.job4me.services.payment;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import miwm.job4me.exceptions.PaymentException;
import miwm.job4me.model.payment.Payment;
import miwm.job4me.repositories.payment.PaymentRepository;
import miwm.job4me.validators.entity.payment.PaymentValidator;
import miwm.job4me.validators.fields.IdValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static miwm.job4me.messages.AppMessages.FRONT_HOST_AZURE;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String recommendationApiKey;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final PaymentRepository paymentRepository;
    private final PaymentValidator paymentValidator;
    private final IdValidator idValidator;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentValidator paymentValidator, IdValidator idValidator) {
        this.paymentRepository = paymentRepository;
        this.paymentValidator = paymentValidator;
        this.idValidator = idValidator;
    }

    @Override
    public Payment save(Payment payment) {
        paymentValidator.validate(payment);
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(String sessionId) {
        Payment payment = getPaymentBySessionId(sessionId);
        payment.setIsPaid(true);
        paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentBySessionId(String sessionId) {
        idValidator.validateStringId(sessionId, "Session");
        return paymentRepository.findBySessionId(sessionId);
    }

    @Override
    public void handleCheckoutPayment(String payload, String sigHeader) {
        Stripe.apiKey = recommendationApiKey;

        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            Session session = null;

            if (dataObjectDeserializer.getObject().isPresent()) {
                session = (Session) dataObjectDeserializer.getObject().get();
            } else {
                throw new PaymentException("Event data object deserializer is empty");
            }

            switch (event.getType()) {
                case "checkout.session.completed": {
                    updatePaymentStatus(session.getId());
                    break;
                }
                default:
                    break;
            }

        } catch (SignatureVerificationException e) {
            throw new PaymentException(e.getMessage(), e);
        }

    }

    @Override
    public Session createJobFairPayment(String customerEmail) {
        Long productQuantity = 1L;
        Long productPrice = 200L;
        String productName = "Utworzenie targów pracy";
        String successUrl = FRONT_HOST_AZURE + "/organizer/payment/success";
        String cancelUrl = FRONT_HOST_AZURE + "/organizer/payment/cancel";
        String productImageUrl = "https://files.stripe.com/links/MDB8YWNjdF8xTzlaYWRJb1RMYU5hVEFqfGZsX2xpdmVfMXpMRkZueXpwc0VQaFdjOXNiU2p3a1Zp00kFAIQX0R";
        String productDescription = "Tworzy targi pracy i umożliwia ich przeprowadzenie.";

        return createPaymentSession(productQuantity, productPrice, productName, successUrl, cancelUrl, productImageUrl, productDescription, customerEmail);
    }

    @Override
    public Session createPaymentSession(Long productQuantity, Long productPrice, String productName, String successUrl, String cancelUrl, String productImageUrl, String productDescription, String customerEmail) {
        Stripe.apiKey = recommendationApiKey;

        try {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.P24)
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.BLIK)
                            .setCustomerEmail(customerEmail)
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(productQuantity)
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setCurrency("pln")
                                                            .setUnitAmount(productPrice)
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                            .setName(productName)
                                                                            .setDescription(productDescription)
                                                                            .addImage(productImageUrl)
                                                                            .build())
                                                            .build())
                                            .build())
                            .setSuccessUrl(successUrl)
                            .setCancelUrl(cancelUrl)
                            .build();

            return Session.create(params);
        } catch (StripeException e) {
            throw new PaymentException(e.getMessage(), e);
        }
    }

}
