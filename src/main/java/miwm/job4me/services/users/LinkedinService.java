package miwm.job4me.services.users;

import miwm.job4me.model.users.LinkedinCheckout;

public interface LinkedinService {

    LinkedinCheckout loginLinkedinAccount();
    String createLinkedinSession(String successUrl);
}
