package miwm.job4me.services.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import miwm.job4me.exceptions.PaymentException;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.services.event.JobFairService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.web.model.event.JobFairDto;
import miwm.job4me.web.model.payment.PaymentCheckout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static miwm.job4me.messages.AppMessages.FRONT_HOST_AZURE;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String recommendationApiKey;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final OrganizerService organizerService;
    private final JobFairService jobFairService;

    public PaymentServiceImpl(OrganizerService organizerService, JobFairService jobFairService) {
        this.organizerService = organizerService;
        this.jobFairService = jobFairService;
    }

    @Override
    public void handleJobFairPaymentSuccess(String paymentRedirectToken) {
        jobFairService.addPaymentForJobFair(paymentRedirectToken, "", true);
    }

    @Override
    public void handleJobFairPaymentCancel(String paymentRedirectToken) {

    }

    @Override
    public PaymentCheckout coordinateJobFairPayment(JobFairDto jobFairDto) {
        JobFairDto jobFair = jobFairService.saveDto(jobFairDto);
        String paymentRedirectToken = UUID.randomUUID().toString();
        String redirectPathSuffix = jobFair.getId() + "/" + paymentRedirectToken;

        Session session = createJobFairPayment(redirectPathSuffix);
        PaymentCheckout paymentCheckout = new PaymentCheckout();
        paymentCheckout.setUrl(session.getUrl());
        jobFairService.addPaymentForJobFair(redirectPathSuffix, paymentRedirectToken, false);

        return paymentCheckout;
    }

    @Override
    public Session createJobFairPayment(String redirectData) {
        Organizer organizer = organizerService.getAuthOrganizer();

        Long productQuantity = 1L;
        Long productPrice = 200L;
        String productName = "Utworzenie targów pracy";
        String successUrl = FRONT_HOST_AZURE + "/organizer/job-fairs/success/" + redirectData;
        String cancelUrl = FRONT_HOST_AZURE + "/organizer/job-fairs/cancel/" + redirectData;
        String productImageUrl = "https://files.stripe.com/links/MDB8YWNjdF8xTzlaYWRJb1RMYU5hVEFqfGZsX2xpdmVfMXpMRkZueXpwc0VQaFdjOXNiU2p3a1Zp00kFAIQX0R";
        String productDescription = "Tworzy targi pracy i umożliwia ich przeprowadzenie.";
        String customerEmail = organizer.getEmail();

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

            Session session = Session.create(params);

            return session;
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            throw new PaymentException(e.getMessage(), e);
        }
    }

}
