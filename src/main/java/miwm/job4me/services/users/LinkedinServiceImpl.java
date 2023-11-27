package miwm.job4me.services.users;

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
    public String loginLinkedinAccount() {
        String client = LINKEDIN_CLIENT_ID + environment.getProperty(LINKEDIN_ID_PARAM);
        String URL = BASIC_LINKEDIN_AUTH_URL + "?" + LINKEDIN_RESPONSE_TYPE + "&" + client + "&" + AZURE_LINKEDIN_REDIRECT_URI + "&" + LINKEDIN_STATE + "&" + LINKEDIN_SCOPE;
        return createLinkedinSession(URL);
    }

    @Override
    public String createLinkedinSession(String successUrl) {
        return successUrl;
    }
}
