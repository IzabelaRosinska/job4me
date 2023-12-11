package miwm.job4me.web.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import miwm.job4me.jwt.JwtConfig;
import miwm.job4me.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import miwm.job4me.model.users.LinkedinCheckout;
import miwm.job4me.model.users.Person;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.LinkedinService;
import miwm.job4me.services.users.UserAuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testGetUrlForLinkedinAccountWhenCalledThenReturnOk() throws Exception {
        // Arrange
        LinkedinCheckout linkedinCheckout = new LinkedinCheckout();
        linkedinCheckout.setUrl("https://www.linkedin.com/oauth/v2");
        when(linkedinService.loginLinkedinAccount()).thenReturn(linkedinCheckout);

        // Act & Assert
        mockMvc.perform(get("/linkedin"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    String jsonResponse = mvcResult.getResponse().getContentAsString();
                    assertTrue(jsonResponse.contains("https://www.linkedin.com/oauth/v2"));
                });
    }

    @Test
    public void testLinkedinCallbackWhenCalledWithValidCodeThenReturnOk() throws Exception {
        // Arrange
        String validCode = "valid_code";
        Person mockPerson = mock(Person.class);
        when(authService.loadUserByUsername(anyString())).thenReturn(mockPerson);
        when(authService.registerLinkedinUser(any(JsonNode.class))).thenReturn(mockPerson);
        when(authService.loginLinkedinUser(anyString())).thenReturn("token");

        // Act & Assert
        mockMvc.perform(get("/auth/linkedin/callback").param("code", validCode))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testLinkedinCallbackWhenCalledWithoutCodeThenReturnBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/auth/linkedin/callback"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLinkedinCallbackWhenCalledWithInvalidCodeThenReturnBadRequest() throws Exception {
        // Arrange
        String invalidCode = "invalid_code";
        when(authService.loadUserByUsername(anyString())).thenThrow(new IOException());

        // Act & Assert
        mockMvc.perform(get("/auth/linkedin/callback").param("code", invalidCode))
                .andExpect(status().isBadRequest());
    }
}
