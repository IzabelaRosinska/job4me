package miwm.job4me.web.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import miwm.job4me.model.users.LinkedinCheckout;
import miwm.job4me.model.users.Account;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.LinkedinService;
import miwm.job4me.services.users.UserAuthenticationService;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static miwm.job4me.messages.AppMessages.*;

@RestController
public class LinkedInController {

    private final Environment environment;
    private final UserAuthenticationService authService;
    private final EmployeeService employeeService;
    private final EmployerService employerService;
    private final LinkedinService linkedinService;
    private final RestTemplate restTemplate;

    public LinkedInController(Environment environment, UserAuthenticationService authService, EmployeeService employeeService, EmployerService employerService, LinkedinService linkedinService, RestTemplate restTemplate) {
        this.environment = environment;
        this.authService = authService;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.linkedinService = linkedinService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/linkedin")
    public ResponseEntity<LinkedinCheckout> getUrlForLinkedinAccount() {
        return ResponseEntity.ok(linkedinService.loginLinkedinAccount());
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

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        entity = new HttpEntity<>(headers);

        ResponseEntity<String> linkedin_response = restTemplate.exchange(
                BASIC_LINKEDIN_PROFILE_URL,
                HttpMethod.GET,
                entity,
                String.class
        );

        String responseBody = linkedin_response.getBody();
        objectMapper = new ObjectMapper();
        jsonNode = objectMapper.readTree(responseBody);
        String email = jsonNode.get("email").asText();
        System.out.println(email);

        Account user = authService.loadUserByUsername(email);
        if(user == null)
            user = authService.registerLinkedinUser(jsonNode);

        String token = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal().equals("anonymousUser")) {
            token = authService.loginLinkedinUser(email);
            response.sendRedirect(FRONT_HOST_AZURE + "/user?role=" + user.getUserRole().toString() + "&token=" + token);
        }
    }
}
