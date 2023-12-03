package miwm.job4me.services.users;

import miwm.job4me.model.users.LinkedinCheckout;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import static miwm.job4me.messages.AppMessages.*;

@Service
public class LinkedinServiceImpl implements LinkedinService {

    private final Environment environment;

    public LinkedinServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public LinkedinCheckout loginLinkedinAccount() {
        String client = LINKEDIN_CLIENT_ID + environment.getProperty(LINKEDIN_ID_PARAM);
        String URL = BASIC_LINKEDIN_AUTH_URL + "?" + LINKEDIN_RESPONSE_TYPE + "&" + client + "&" + NEW_LINKEDIN_REDIRECT_URI + "&" + LINKEDIN_STATE + "&" + LINKEDIN_SCOPE;
        LinkedinCheckout checkout = new LinkedinCheckout();
        checkout.setUrl(createLinkedinSession(URL));
        return checkout;
    }

    @Override
    public String createLinkedinSession(String successUrl) {
        return successUrl;
    }
}
