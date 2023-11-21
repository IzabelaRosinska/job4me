package miwm.job4me.messages;

public class AppMessages {

    public static final String JWT_TOKEN_NAME = "token";
    public static final String BACKEND_HOST = "http://localhost:8080";
    public static final String FRONT_HOST = "http://localhost:4200";
    public static final String FRONT_HOST_AZURE = "https://mango-moss-0c13e2b03-32.westeurope.3.azurestaticapps.net";

    public static final String BASIC_LINKEDIN_AUTH_URL = "https://www.linkedin.com/oauth/v2/authorization";
    public static final String BASIC_LINKEDIN_TOKEN_URL = "https://www.linkedin.com/oauth/v2/accessToken";
    public static final String BASIC_LINKEDIN_PROFILE_URL = "https://api.linkedin.com/v2/userinfo";
    public static final String BASIC_LINKEDIN_REDIRECT_URI = "redirect_uri=http://localhost:8080/linkedin/authorized";
    public static final String NEW_LINKEDIN_REDIRECT_URI = "redirect_uri=http://localhost:8080/auth/linkedin/callback";
    public static final String AZURE_LINKEDIN_REDIRECT_URI = "redirect_uri=https://job4me.azurewebsites.net/linkedin/authorized";

    public static final String LINKEDIN_RESPONSE_TYPE = "response_type=code";
    public static final String LINKEDIN_GRANT_TYPE = "grant_type=authorization_code";
    public static final String LINKEDIN_STATE = "state=foobar";
    public static final String LINKEDIN_SCOPE = "scope=openid%20profile%20email";
    public static final String LINKEDIN_AUTH_CODE = "code=";
    public static final String LINKEDIN_CLIENT_ID = "client_id=";
    public static final String LINKEDIN_CLIENT_SECRET = "client_secret=";

}
