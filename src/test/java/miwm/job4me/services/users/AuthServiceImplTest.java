package miwm.job4me.services.users;

import miwm.job4me.jwt.JwtConfig;
import miwm.job4me.model.users.Employer;
import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.repositories.users.EmployerRepository;
import miwm.job4me.repositories.users.OrganizerRepository;
import miwm.job4me.repositories.users.VerificationTokenRepository;
import miwm.job4me.services.tokens.PasswordResetTokenService;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.web.context.WebApplicationContext;
import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WithMockUser
@ContextConfiguration
@SpringBootTest
class AuthServiceImplTest {

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
}


