package miwm.job4me.services.users;


import miwm.job4me.jwt.JwtConfig;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Person;
import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.repositories.users.EmployerRepository;
import miwm.job4me.repositories.users.OrganizerRepository;
import miwm.job4me.repositories.users.VerificationTokenRepository;
import miwm.job4me.services.tokens.PasswordResetTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WithMockUser
@ContextConfiguration
@SpringBootTest
class UserAuthenticationServiceTest {

    @MockBean
    JavaMailSender javaMailSenderMock;
    @MockBean
    SecretKey secretKey;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    JwtConfig jwtConfig;

    @Autowired
    WebApplicationContext context;

    @InjectMocks
    UserAuthenticationService userAuthenticationService;

    @Mock
    EmployerService employerService;
    @Mock
    EmployeeService employeeService;
    @Mock
    OrganizerService organizerService;
    @Mock
    PasswordResetTokenService passwordResetTokenService;

    @Mock
    EmployerRepository employerRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    OrganizerRepository organizerRepository;
    @Mock
    VerificationTokenRepository verificationTokenRepository;

    Employer employer;


    @BeforeEach
    void setUp() {
        employer = Employer
                .builder()
                .id(1L)
                .companyName("TestCompany")
                .email("itds.polska@wp.pl")
                .password("password")
                .userRole(new SimpleGrantedAuthority("ROLE_EMPLOYER"))
                .description("Test description")
                .displayDescription("Short test description")
                .contactEmail("itds.polska@wp.pl")
                .telephone("123456789")
                .address("ul. Testowa 5, 50-123 Testowo")
                .photo("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.creativefabrica.com%2Fpl%2Fproduct%2Fperson-icon-13%2F&psig=AOvVaw2D_r8bNfGFAUAv6AI0QXjD&ust=1701606266910000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCOi9prrf8IIDFQAAAAAdAAAAABAE")
                .build();
    }

    @Test
    void loadUserByUsername() {
        //when
        when(employerRepository.selectEmployerByUsername(any())).thenReturn(employer);
        when(employeeRepository.selectEmployeeByUsername(any())).thenReturn(null);
        when(organizerRepository.selectOrganizerByUsername(any())).thenReturn(null);
        Person loadedPerson = userAuthenticationService.loadUserByUsername("itds.polska@wp.pl");
        //then
        assertNotNull(loadedPerson);
        assertEquals(loadedPerson.getClass(), Employer.class);
    }
/*
    private String anyString() {
    }


    @Test
    public void existentUserCanGetTokenAndAuthentication() throws Exception {
       // String username = "AC";
        //String password = "passAC";

        //String body = "{\"username\":" + "\"" + username + "\"" + "," + "\"password\":" + "\"" + password +  "\"" + "}";
        //System.out.print(body);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login").with(csrf()).content("{\"username\": \"AC\", \"password\": \"passAC\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(302))
                .andReturn();

       // MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
       //         .content(body))
          //      .andExpect(status().isOk()).andReturn();

        //MvcResult result =

         //      mockMvc
          //              .perform(post("/login")
          //              .with(user("admin").password("pass").roles("USER","ADMIN")))

        String token = result.getResponse().getHeader("Authorization");
        //String response = result.getResponse().getContentAsString();
        System.out.print("TOKEN " + token + "\n");
        //response = response.replace("{\"access_token\": \"", "");
       // String token = response.replace("\"}", "");
//
        //mockMvc.perform(MockMvcRequestBuilders.get("/test")
        //                .header("Authorization", "Bearer " + token))
         //       .andExpect(status().isOk());


    }

    @Test
    void getAuthenticatedProviderId() {
    }

    @Test
    void getAuthenticatedClientId() {
    }

 */


}


