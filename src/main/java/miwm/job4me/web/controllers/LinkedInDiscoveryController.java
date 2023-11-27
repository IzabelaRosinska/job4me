package miwm.job4me.web.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import miwm.job4me.model.users.Person;
import miwm.job4me.security.ApplicationUserRole;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static miwm.job4me.messages.AppMessages.*;

@Controller
@CrossOrigin
public class LinkedInDiscoveryController {

    private Environment environment;
    private final UserAuthenticationService authService;
    private final EmployeeService employeeService;
    private final EmployerService employerService;
    private final RestTemplate restTemplate;

    @Autowired
    private LinkedInConnectionFactory connectionFactory;

    public LinkedInDiscoveryController(Environment environment, UserAuthenticationService authService, EmployeeService employeeService, EmployerService employerService, RestTemplate restTemplate) {
        this.environment = environment;
        this.authService = authService;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.restTemplate = restTemplate;
    }

    /*
    @GetMapping("/linkedin/signin")
    public void proxyLinkedInRequest(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        String client = LINKEDIN_CLIENT_ID + environment.getProperty("spring.social.linkedin.app-id");
        String URL_init = BASIC_LINKEDIN_AUTH_URL + "?" + LINKEDIN_RESPONSE_TYPE + "&" + client + "&" + AZURE_LINKEDIN_REDIRECT_URI + "&" + LINKEDIN_STATE + "&" + LINKEDIN_SCOPE;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        httpServletResponse.setHeader(HttpHeaders.LOCATION,  URL_init);
        httpServletResponse.setStatus(HttpServletResponse.SC_FOUND);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, OPTIONS, DELETE, PUT");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
    }

    @GetMapping("/linkedin/signin")
    public ResponseEntity<Void> linkedIn2Request() throws IOException {
        String client = LINKEDIN_CLIENT_ID + environment.getProperty("spring.social.linkedin.app-id");
        String URL_init = BASIC_LINKEDIN_AUTH_URL + "?" + LINKEDIN_RESPONSE_TYPE + "&" + client + "&" + AZURE_LINKEDIN_REDIRECT_URI + "&" + LINKEDIN_STATE + "&" + LINKEDIN_SCOPE;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", URL_init);
        headers.setOrigin("https://www.linkedin.com");
        headers.setAccessControlMaxAge(3600);

        List<HttpMethod> allowedMethods = Arrays.asList(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS, HttpMethod.PATCH);
        headers.setAccessControlAllowMethods(allowedMethods);

        List<String> allowedHeaders = Arrays.asList("*");
        headers.setAccessControlAllowHeaders(allowedHeaders);


        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/linkedin/signin")
    public RedirectView proxyLinkedIn3Request(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        String client = LINKEDIN_CLIENT_ID + environment.getProperty("spring.social.linkedin.app-id");
        String URL_init = BASIC_LINKEDIN_AUTH_URL + "?" + LINKEDIN_RESPONSE_TYPE + "&" + client + "&" + AZURE_LINKEDIN_REDIRECT_URI + "&" + LINKEDIN_STATE + "&" + LINKEDIN_SCOPE;
        return new RedirectView(URL_init);
    }
    */

    @GetMapping("/linkedin/signin")
    public String redirectToLinkedInForAuth(HttpServletRequest request) throws UnsupportedEncodingException {
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.set(LINKEDIN_RESPONSE_TYPE_PARAM, PURE_LINKEDIN_RESPONSE_TYPE);
        parameters.set(LINKEDIN_REDIRECT_URI_PARAM, PURE_AZURE_LINKEDIN_REDIRECT_URI);
        parameters.setState(PURE_LINKEDIN_STATE);
        parameters.setScope(PURE_LINKEDIN_SCOPE);

        String authorizeUrl = oauthOperations.buildAuthorizeUrl(parameters);
        return "redirect:" + authorizeUrl;
    }

    @GetMapping("/auth/linkedin/callback")
    public void linkedinCallback(@RequestParam(name = "code", required = false) String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String authorizationCode = LINKEDIN_AUTH_CODE + code;
        String client = LINKEDIN_CLIENT_ID + environment.getProperty(LINKEDIN_ID_PARAM);
        String secret = LINKEDIN_CLIENT_SECRET + environment.getProperty(LINKEDIN_SECRET_PARAM);
        String URL = BASIC_LINKEDIN_TOKEN_URL + "?" + authorizationCode + "&" + LINKEDIN_GRANT_TYPE + "&" + client + "&" + secret + "&" + AZURE_LINKEDIN_REDIRECT_URI;

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                entity,
                String.class
        );
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response2.getBody());
        String accessToken = jsonNode.get("access_token").asText();

        show_user(request, response, accessToken);
    }

    private void show_user(HttpServletRequest request, HttpServletResponse response, String accessToken) throws IOException {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> linkedin_response = restTemplate.exchange(
                    BASIC_LINKEDIN_PROFILE_URL,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            String responseBody = linkedin_response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String email = jsonNode.get("email").asText();
            System.out.println(email);

            Person user = authService.loadUserByUsername(email);
            if(user == null)
                user = authService.registerLinkedinUser(jsonNode);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication.getPrincipal().equals("anonymousUser")) {
                String token = authService.loginLinkedinUser(email);
                response.getWriter().write(user.getUserRole().toString() + ';' + token);
                response.setHeader("Authorization", "Token " + token);
            }

            if(user.getUserRole().equals(ApplicationUserRole.EMPLOYEE_ENABLED.getUserRole())){
                employeeService.saveEmployeeDataFromLinkedin(user, jsonNode);
                response.sendRedirect(FRONT_HOST_AZURE + "/employee/account");
            } else if(user.getUserRole().equals(ApplicationUserRole.EMPLOYER_ENABLED.getUserRole())) {
                employerService.saveEmployerDataFromLinkedin(user, jsonNode);
                response.sendRedirect(FRONT_HOST_AZURE + "/employer/account");
            }
    }

}
