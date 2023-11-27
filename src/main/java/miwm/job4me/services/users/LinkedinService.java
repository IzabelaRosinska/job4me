package miwm.job4me.services.users;

import org.springframework.stereotype.Service;

@Service
public class LinkedinService {

    public String loginLinkedinAccount() {
        return createLinkedinSession("https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=77ebvrc0c0fjtq&redirect_uri=https://job4me.azurewebsites.net/auth/linkedin/callback&state=foobar&scope=openid%20profile%20email");
    }

    public String createLinkedinSession(String successUrl) {
        return successUrl;
    }
}
