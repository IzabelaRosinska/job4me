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
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static miwm.job4me.messages.AppMessages.*;


@RestController
@RequestMapping("/linkedin")
public class LinkedInController {

    private final UserAuthenticationService authService;
    private final EmployeeService employeeService;
    private final EmployerService employerService;
    private final Environment environment;

    public LinkedInController(UserAuthenticationService authService, EmployeeService employeeService, EmployerService employerService, Environment environment) {
        this.authService = authService;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.environment = environment;
    }
/*
    @GetMapping("/signin")
    public void signInLinkedIn(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String client = LINKEDIN_CLIENT_ID + environment.getProperty("spring.social.linkedin.app-id");
        String URL = BASIC_LINKEDIN_AUTH_URL + "?" + LINKEDIN_RESPONSE_TYPE + "&" + client + "&" + BASIC_LINKEDIN_REDIRECT_URI + "&" + LINKEDIN_STATE + "&" + LINKEDIN_SCOPE;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.sendRedirect(URL);
    }

    @GetMapping("/authorized")
    public void linkedinAuthorized(HttpServletResponse response, HttpServletRequest request, @RequestParam(name="code", required=false) String code, @RequestParam(name="state", required=false) String state) throws IOException {
        String authorizationCode = LINKEDIN_AUTH_CODE + code;
        String client = LINKEDIN_CLIENT_ID + environment.getProperty("spring.social.linkedin.app-id");
        String secret = LINKEDIN_CLIENT_SECRET + environment.getProperty("spring.social.linkedin.app-secret");
        String URL = BASIC_LINKEDIN_TOKEN_URL + "?" + authorizationCode + "&" + LINKEDIN_GRANT_TYPE + "&" + client + "&" + secret + "&" + BASIC_LINKEDIN_REDIRECT_URI;

        String accessToken = getLinkedinAccessToken(URL);

        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build()) {
            final HttpGet httpGet = new HttpGet(BASIC_LINKEDIN_PROFILE_URL);
            httpGet.addHeader("Authorization", "Bearer " + accessToken);
            httpGet.setHeader("Access-Control-Allow-Origin", request.getHeader("*"));

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
                    Cookie tokenCookie = new Cookie(AppMessages.JWT_TOKEN_NAME, token);
                    tokenCookie.setHttpOnly(true);
                    tokenCookie.setPath("/");
                    response.addCookie(tokenCookie);
                    response.getWriter().write(user.getUserRole().toString() + ';' + token);
                    response.setHeader("Authorization", "Token " + token);
                    response.setHeader("Access-Control-Allow-Origin", request.getHeader("*"));
                }

                if(user.getUserRole().equals(ApplicationUserRole.EMPLOYEE_ENABLED.getUserRole())){
                    employeeService.saveEmployeeDataFromLinkedin(user, jsonNode);
                    response.sendRedirect(FRONT_HOST + "/employee/account");
                } else if(user.getUserRole().equals(ApplicationUserRole.EMPLOYER_ENABLED.getUserRole())) {
                    employerService.saveEmployerDataFromLinkedin(user, jsonNode);
                    response.sendRedirect(FRONT_HOST + "/employer/account");
                }
            }
        }
    }


    private String getLinkedinAccessToken(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(content.toString());
        String accessToken = jsonNode.get("access_token").asText();
        return accessToken;
    }

 */

}
