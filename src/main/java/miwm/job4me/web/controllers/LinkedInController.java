package miwm.job4me.web.controllers;


import miwm.job4me.services.users.*;
import miwm.job4me.model.users.LinkedinCheckout;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/linkedin")
public class LinkedInController {

    private final UserAuthenticationService authService;
    private final EmployeeService employeeService;
    private final EmployerService employerService;
    private final LinkedinServiceImpl linkedinServiceImpl;
    private final Environment environment;

    public LinkedInController(UserAuthenticationService authService, EmployeeService employeeService, EmployerService employerService, LinkedinServiceImpl linkedinServiceImpl, Environment environment) {
        this.authService = authService;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.linkedinServiceImpl = linkedinServiceImpl;
        this.environment = environment;
    }

    @GetMapping()
    public ResponseEntity<LinkedinCheckout> getUrlForLinkedinAccount() {
        LinkedinCheckout checkout = new LinkedinCheckout();
        checkout.setUrl(linkedinServiceImpl.loginLinkedinAccount());
        return ResponseEntity.ok(checkout);
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
