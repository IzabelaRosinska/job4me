package miwm.job4me.services.users;

import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.Jwts;
import miwm.job4me.exceptions.AuthException;
import miwm.job4me.exceptions.UserAlreadyExistException;
import miwm.job4me.jwt.JwtConfig;
import miwm.job4me.messages.AppMessages;
import miwm.job4me.messages.UserMessages;
import miwm.job4me.model.VerificationToken;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.model.users.Person;
import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.repositories.users.EmployerRepository;
import miwm.job4me.repositories.users.OrganizerRepository;
import miwm.job4me.repositories.users.VerificationTokenRepository;
import miwm.job4me.security.ApplicationUserRole;
import miwm.job4me.web.model.users.RegisterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.Date;

@Service
public class UserAuthenticationService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final EmployerRepository employerRepository;
    private final OrganizerRepository organizerRepository;
    private final VerificationTokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public UserAuthenticationService(EmployeeRepository clientRepository, PasswordEncoder passwordEncoder, EmployerRepository employerRepository, OrganizerRepository organizerRepository, VerificationTokenRepository tokenRepository, JwtConfig jwtConfig, SecretKey secretKey) {
        this.employeeRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.employerRepository = employerRepository;
        this.organizerRepository = organizerRepository;
        this.tokenRepository = tokenRepository;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public Person loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.selectEmployeeByUsername(username);
        Employer employer = employerRepository.selectEmployerByUsername(username);
        Organizer organizer = organizerRepository.selectOrganizerByUsername(username);
        if(employee != null)
            return employee;
        else if(employer != null)
            return employer;
        else if(organizer != null)
            return organizer;
        else
            return null;
    }

    public Employee getAuthenticatedEmployee() throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.selectEmployeeByUsername(authentication.getName());
        if(employee == null)
            throw new AuthException(UserMessages.BANNED_RESOURCES);
        return employee;
    }

    public Employer getAuthenticatedEmployer() throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employer employer = employerRepository.selectEmployerByUsername(authentication.getName());
        if(employer == null)
            throw new AuthException(UserMessages.BANNED_RESOURCES);
        return employer;
    }

    public Person getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.selectEmployeeByUsername(authentication.getName());
        return employee;
    }


    public Organizer getAuthenticatedOrganizer() throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Organizer organizer = organizerRepository.selectOrganizerByUsername(authentication.getName());
        if(organizer == null)
            throw new AuthException(UserMessages.BANNED_RESOURCES);
        return organizer;
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }


    public Person registerNewUserAccount(RegisterData userDto) throws UserAlreadyExistException {
        if (!emailExist(userDto.getUsername())) {
            if(userDto.getRole().equals("EMPLOYEE")) {
                Employee newEmployee = Employee.builder()
                        .email(userDto.getUsername())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .locked(true)
                        .userRole(ApplicationUserRole.EMPLOYEE.getUserRole()).build();
                employeeRepository.save(newEmployee);
                return newEmployee;
            }else if(userDto.getRole().equals("EMPLOYER")) {
                Employer newEmployer = Employer.builder()
                        .email(userDto.getUsername())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .locked(true)
                        .userRole(ApplicationUserRole.EMPLOYER.getUserRole()).build();
                employerRepository.save(newEmployer);
                return newEmployer;
            }else if(userDto.getRole().equals("ORGANIZER")) {
                Organizer newOrganizer = Organizer.builder()
                        .email(userDto.getUsername())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .locked(true)
                        .userRole(ApplicationUserRole.ORGANIZER.getUserRole()).build();
                organizerRepository.save(newOrganizer);
                return newOrganizer;
            }
        }else
            throw new UserAlreadyExistException(UserMessages.ACCOUNT_FOR_EMAIL_EXISTS + userDto.getUsername());

        return null;
    }

    private boolean emailExist(String email) {
        return employeeRepository.selectEmployeeByUsername(email) != null || employerRepository.selectEmployerByUsername(email) != null || organizerRepository.selectOrganizerByUsername(email) != null ;
    }

    public void unlockEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.selectEmployeeByUsername(employee.getEmail());
        savedEmployee.setUserRole(new SimpleGrantedAuthority("ROLE_EMPLOYEE_ENABLED"));
        savedEmployee.setLocked(false);
        employeeRepository.save(savedEmployee);
    }

    public void unlockEmployer(Employer employer) {
        Employer savedEmployer = employerRepository.selectEmployerByUsername(employer.getEmail());
        savedEmployer.setUserRole(new SimpleGrantedAuthority("ROLE_EMPLOYER_ENABLED"));
        savedEmployer.setLocked(false);
        employerRepository.save(savedEmployer);
    }

    public void createVerificationToken(Person person, String token) {
        VerificationToken myToken = VerificationToken.builder().token(token).person(person).build();
        tokenRepository.save(myToken);
    }

    public Person registerLinkedinUser(JsonNode jsonNode) {
        Employee newEmployee = Employee.builder()
                .email(jsonNode.get("email").asText())
                .locked(false)
                .userRole(ApplicationUserRole.EMPLOYEE_ENABLED.getUserRole()).build();
        employeeRepository.save(newEmployee);
        return newEmployee;
    }

    public Cookie loginLinkedinUser(String email) {
        Employee employee = (Employee)loadUserByUsername(email);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, "", employee.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", auth.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();
        Cookie tokenCookie = new Cookie(AppMessages.JWT_TOKEN_NAME, token);
        return tokenCookie;
    }
}
