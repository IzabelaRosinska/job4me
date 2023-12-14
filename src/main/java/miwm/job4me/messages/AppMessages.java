package miwm.job4me.messages;

public class AppMessages {

    public static final String BACKEND_HOST = "http://localhost:8080";
    public static final String BACKEND_HOST_AZURE = "https://job4me.azurewebsites.net";
    public static final String FRONT_HOST = "http://localhost:4200";
    public static final String FRONT_HOST_AZURE = "https://mango-moss-0c13e2b03.3.azurestaticapps.net";
    public static final String FRONT_BRANCH_HOST_AZURE = "https://mango-moss-0c13e2b03-32.westeurope.3.azurestaticapps.net";
    public static final String LINKEDIN_HOST = "https://www.linkedin.com";

    public static final String LINKEDIN_ID_PARAM = "spring.social.linkedin.app-id";
    public static final String LINKEDIN_SECRET_PARAM = "spring.social.linkedin.app-secret";

    public static final String BASIC_LINKEDIN_AUTH_URL = "https://www.linkedin.com/oauth/v2/authorization";
    public static final String BASIC_LINKEDIN_TOKEN_URL = "https://www.linkedin.com/oauth/v2/accessToken";
    public static final String BASIC_LINKEDIN_PROFILE_URL = "https://api.linkedin.com/v2/userinfo";
    public static final String NEW_LINKEDIN_REDIRECT_URI = "redirect_uri=http://localhost:8080/auth/linkedin/callback";
    public static final String AZURE_LINKEDIN_REDIRECT_URI = "redirect_uri=https://job4me.azurewebsites.net/auth/linkedin/callback";
    public static final String PURE_AZURE_LINKEDIN_REDIRECT_URI = "https://job4me.azurewebsites.net/auth/linkedin/callback";
    public static final String LINKEDIN_REDIRECT_URI_PARAM = "redirect_uri";

    public static final String LINKEDIN_RESPONSE_TYPE = "response_type=code";
    public static final String LINKEDIN_RESPONSE_TYPE_PARAM = "response_type";
    public static final String PURE_LINKEDIN_RESPONSE_TYPE = "code";
    public static final String LINKEDIN_GRANT_TYPE = "grant_type=authorization_code";
    public static final String LINKEDIN_STATE = "state=foobar";
    public static final String PURE_LINKEDIN_STATE = "foobar";
    public static final String LINKEDIN_SCOPE = "scope=openid%20profile%20email";
    public static final String PURE_LINKEDIN_SCOPE = "openid profile email";
    public static final String LINKEDIN_AUTH_CODE = "code=";
    public static final String LINKEDIN_CLIENT_ID = "client_id=";
    public static final String LINKEDIN_CLIENT_SECRET = "client_secret=";
    public static final String LINKEDIN_USER_PASSWORD = "";

    public static final Boolean CORS_ALLOW_CREDENTIALS = true;
    public static final Integer CORS_MAX_AGE = 3600;
    public static final String CORS_ALLOW_ANY = "*";

    public static final String MAIL_HOST = "smtp.gmail.com";
    public static final String MAIL_USERNAME = "my.gmail@gmail.com";
    public static final String MAIL_PASSWORD_PLACEHOLDER = "password";
    public static final String MAIL_PROTOCOL = "smtp";
    public static final String MAIL_OPTION_ENABLE = "true";
    public static final String MAIL_NO_REPLY = "noreply@job4meeapp.com";
    public static final String MAIL_PROTOCOL_PARAM = "mail.transport.protocol";
    public static final String MAIL_AUTH_PARAM = "mail.smtp.auth";
    public static final String MAIL_SMTP_PARAM = "mail.smtp.starttls.enable";
    public static final String MAIL_DEBUG_PARAM = "mail.debug";
    public static final Integer MAIL_PORT = 587;

    public static final String ROLE_ORGANIZER_ENABLED = "ROLE_ORGANIZER_ENABLED";
    public static final String ROLE_EMPLOYER_ENABLED = "ROLE_EMPLOYER_ENABLED";
    public static final String ROLE_EMPLOYEE_ENABLED = "ROLE_EMPLOYEE_ENABLED";
    public static final String ROLE_EMPLOYER = "ROLE_EMPLOYER";
    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    public static final String ROLE_ORGANIZER = "ROLE_ORGANIZER";
    public static final String ORGANIZER_ENABLED = "ORGANIZER_ENABLED";
    public static final String EMPLOYER_ENABLED = "EMPLOYER_ENABLED";
    public static final String EMPLOYEE_ENABLED = "EMPLOYEE_ENABLED";
    public static final String ORGANIZER = "ORGANIZER";
    public static final String EMPLOYER = "EMPLOYER";
    public static final String EMPLOYEE = "EMPLOYEE";
    public static final String ADMIN = "ADMIN";

    public static final String ERROR_URL = "/";
    public static final String SUCCESS_URL = "/";
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String UPDATE_PASSWORD_URL = "/password-update?token=";
    public static final String CHANGE_PASSWORD_URL = "/password-change?token=";
    public static final String REGISTRATION_URL = "/registration?token=";
    public static final String JWT_TOKEN_NAME = "token";
    public static final String TEST_USER = "user";

    public static final String INCORRECT_CREDENTIALS = "message.badCredentials";

    public static final String STRIPE_HOST = "https://checkout.stripe.com";

}
