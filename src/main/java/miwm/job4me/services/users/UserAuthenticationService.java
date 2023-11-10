package miwm.job4me.services.users;

import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.Jwts;
import miwm.job4me.emails.EMailService;
import miwm.job4me.exceptions.AuthException;
import miwm.job4me.exceptions.UserAlreadyExistException;
import miwm.job4me.jwt.JwtConfig;
import miwm.job4me.messages.AppMessages;
import miwm.job4me.messages.UserMessages;
import miwm.job4me.model.token.PasswordResetToken;
import miwm.job4me.model.token.VerificationToken;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.model.users.Person;
import miwm.job4me.repositories.users.*;
import miwm.job4me.security.ApplicationUserRole;
import miwm.job4me.web.model.users.RegisterData;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserAuthenticationService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final EmployerRepository employerRepository;
    private final OrganizerRepository organizerRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordTokenRepository passwordTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final EMailService emailService;

    public UserAuthenticationService(EmployeeRepository clientRepository, PasswordEncoder passwordEncoder, EmployerRepository employerRepository, OrganizerRepository organizerRepository, VerificationTokenRepository verificationTokenRepository, PasswordTokenRepository passwordTokenRepository, JwtConfig jwtConfig, SecretKey secretKey, EMailService emailService) {
        this.employeeRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.employerRepository = employerRepository;
        this.organizerRepository = organizerRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.emailService = emailService;
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
        return verificationTokenRepository.findByToken(VerificationToken);
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
        verificationTokenRepository.save(myToken);
    }

    public void createPasswordResetTokenForUser(Person person, String token) {
        PasswordResetToken resetToken = PasswordResetToken.builder().token(token).person(person).build();
        passwordTokenRepository.save(resetToken);
    }

    public void sendResetToken(Person person, String token, String contextPath) {
        String recipientAddress = person.getEmail();
        String subject = "Reset your password";
        String confirmationUrl = contextPath + "/changePassword?token=" + token;
        String text = "Reset your password\n Please click link below to change your password.\n\n" + "http://localhost:8080" + confirmationUrl;

        emailService.sendSimpleMessage(recipientAddress, subject, text);
    }

    public String validatePasswordResetToken(String token) {
        PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        return !isTokenFound(passToken) ? "invalidToken" : isTokenExpired(passToken) ? "expired" : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    public Person getUserByPasswordResetToken(String token) {
        Optional employee = employeeRepository.getEmployeeByToken(token);
        Optional employer = employerRepository.getEmployerByToken(token);
        Optional organizer = organizerRepository.getOrganizerByToken(token);
        if(employee.isPresent())
            return (Person)employee.get();
        else if(employer.isPresent())
            return (Person)employer.get();
        else if(organizer.isPresent())
            return (Person)organizer.get();
        return null;
    }

    public void changeUserPassword(Person person, String password) {
        Employee employee = employeeRepository.selectEmployeeByUsername(person.getEmail());
        Employer employer = employerRepository.selectEmployerByUsername(person.getEmail());
        Organizer organizer = organizerRepository.selectOrganizerByUsername(person.getEmail());
        if(employee != null) {
            employee.setPassword(passwordEncoder.encode(password));
            employeeRepository.save(employee);
        }else if(employer != null) {
            employer.setPassword(passwordEncoder.encode(password));
            employerRepository.save(employer);
        }else if(organizer != null) {
            organizer.setPassword(passwordEncoder.encode(password));
            organizerRepository.save(organizer);
        }
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
