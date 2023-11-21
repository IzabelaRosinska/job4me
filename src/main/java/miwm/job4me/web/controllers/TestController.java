package miwm.job4me.web.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import miwm.job4me.messages.AppMessages;
import miwm.job4me.model.users.Person;
import miwm.job4me.security.ApplicationUserRole;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.UserAuthenticationService;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static miwm.job4me.messages.AppMessages.*;

@Controller
public class TestController {

    private Environment environment;
    private final UserAuthenticationService authService;
    private final EmployeeService employeeService;
    private final EmployerService employerService;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private LinkedInConnectionFactory connectionFactory;

    public TestController(Environment environment, UserAuthenticationService authService, EmployeeService employeeService, EmployerService employerService) {
        this.environment = environment;
        this.authService = authService;
        this.employeeService = employeeService;
        this.employerService = employerService;
    }

    @GetMapping("/linkedin/signin")
    public String redirectToLinkedInForAuth(HttpServletRequest request) throws UnsupportedEncodingException {
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.set("response_type", "code");
        parameters.set("redirect_uri", "https://job4me.azurewebsites.net/auth/linkedin/callback");
        parameters.setState("foobar");
        parameters.setScope("openid profile email");

        String authorizeUrl = oauthOperations.buildAuthorizeUrl(parameters);
        return "redirect:" + authorizeUrl;
    }

    @GetMapping("/auth/linkedin/callback")
    @CrossOrigin(origins = "https://mango-moss-0c13e2b03-32.westeurope.3.azurestaticapps.net")
    public void linkedinCallback(@RequestParam("code") String authorizationCode, HttpServletRequest request, HttpServletResponse response) {
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        String currentRedirectUri = UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request))
                .replaceQuery(null)
                .build()
                .toUriString();

        AccessGrant accessGrant = oauthOperations.exchangeForAccess(authorizationCode, currentRedirectUri, null);

        String accessToken = accessGrant.getAccessToken();
        //System.out.print(accessToken);
        show_user(request, response, accessToken);
        /*
        ConnectionData connectionData = new ConnectionData(
                "linkedin", // ProviderId (e.g., linkedin)
                "uniqueProviderUserId", // Unique provider user ID
                "displayName", // Display name
                "profileUrl", // Profile URL
                "imageUrl", // Image URL
                accessToken, // Access token
                null, // Secret (This might not be available in AccessGrant)
                null, // Refresh token (This might not be available in AccessGrant)
                null // Expire time (This might not be available in AccessGrant)
        );

        ConnectionFactory<LinkedIn> linkedInConnectionFactory = new LinkedInConnectionFactory(
                environment.getProperty("spring.social.linkedin.app-id"), // Replace with your LinkedIn client ID
                environment.getProperty("spring.social.linkedin.app-secret") // Replace with your LinkedIn client secret
        );
        Connection<LinkedIn> connection = linkedInConnectionFactory.createConnection(connectionData);
        connectionRepository.addConnection(connection);

        return "redirect:/profile";

         */
    }

    private void show_user(HttpServletRequest request, HttpServletResponse response, String accessToken) {
        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build()) {
            final HttpGet httpGet = new HttpGet(BASIC_LINKEDIN_PROFILE_URL);
            httpGet.addHeader("Authorization", "Bearer " + accessToken);
            //httpGet.setHeader("Access-Control-Allow-Origin", request.getHeader("*"));

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody.toString());
                String email = jsonNode.get("email").asText();

                Person user = authService.loadUserByUsername(email);
                if(user == null)
                    user = authService.registerLinkedinUser(jsonNode);

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if(authentication.getPrincipal().equals("anonymousUser")) {
                    String token = authService.loginLinkedinUser(email);
                    //Cookie tokenCookie = new Cookie(AppMessages.JWT_TOKEN_NAME, token);
                    //tokenCookie.setHttpOnly(true);
                    //tokenCookie.setPath("/");
                    //response.addCookie(tokenCookie);
                    response.getWriter().write(user.getUserRole().toString() + ';' + token);
                    response.setHeader("Authorization", "Token " + token);
                    //response.setHeader("Access-Control-Allow-Origin", request.getHeader("*"));
                }

                if(user.getUserRole().equals(ApplicationUserRole.EMPLOYEE_ENABLED.getUserRole())){
                    employeeService.saveEmployeeDataFromLinkedin(user, jsonNode);
                    response.sendRedirect(FRONT_HOST_AZURE + "/employee/account");
                } else if(user.getUserRole().equals(ApplicationUserRole.EMPLOYER_ENABLED.getUserRole())) {
                    employerService.saveEmployerDataFromLinkedin(user, jsonNode);
                    response.sendRedirect(FRONT_HOST_AZURE + "/employer/account");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
