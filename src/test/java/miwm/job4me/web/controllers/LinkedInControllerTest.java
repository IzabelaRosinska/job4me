package miwm.job4me.web.controllers;

import miwm.job4me.jwt.JwtConfig;
import miwm.job4me.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.LinkedinService;
import miwm.job4me.services.users.UserAuthenticationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = LinkedInController.class,
        excludeFilters =
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtUsernameAndPasswordAuthenticationFilter.class))
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class LinkedInControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Environment environment;

    @MockBean
    private UserAuthenticationService authService;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployerService employerService;

    @MockBean
    private LinkedinService linkedinService;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    SecretKey secretKey;

    @MockBean
    JwtConfig jwtConfig;

}
