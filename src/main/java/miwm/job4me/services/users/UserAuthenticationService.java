package miwm.job4me.services.users;

import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.Jwts;
import miwm.job4me.emails.EMailService;
import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.exceptions.UserAlreadyExistException;
import miwm.job4me.jwt.JwtConfig;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.token.PasswordResetToken;
import miwm.job4me.model.token.VerificationToken;
import miwm.job4me.model.users.*;
import miwm.job4me.repositories.users.*;
import miwm.job4me.security.ApplicationUserRole;
import miwm.job4me.services.tokens.PasswordResetTokenService;
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
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static miwm.job4me.messages.AppMessages.*;
import static miwm.job4me.messages.EmailMessages.resetPasswordEmailSubject;
import static miwm.job4me.messages.EmailMessages.resetPasswordEmailText;

@Service
public class UserAuthenticationService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final EmployerRepository employerRepository;
    private final OrganizerRepository organizerRepository;
    private final AdminRepository adminRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final EMailService emailService;
    private final EmployeeService employeeService;
    private final EmployerService employerService;
    private final OrganizerService organizerService;
    private final PasswordResetTokenService passwordResetTokenService;

    public UserAuthenticationService(EmployeeRepository clientRepository, PasswordEncoder passwordEncoder, EmployerRepository employerRepository, OrganizerRepository organizerRepository, AdminRepository adminRepository, VerificationTokenRepository verificationTokenRepository, JwtConfig jwtConfig, SecretKey secretKey, EMailService emailService, EmployeeService employeeService, EmployerService employerService, OrganizerService organizerService, PasswordResetTokenService passwordResetTokenService) {
        this.employeeRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.employerRepository = employerRepository;
        this.organizerRepository = organizerRepository;
        this.adminRepository = adminRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.emailService = emailService;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.organizerService = organizerService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @Override
    public Person loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username != null) {
            Employee employee = employeeRepository.selectEmployeeByUsername(username);
            Employer employer = employerRepository.selectEmployerByUsername(username);
            Organizer organizer = organizerRepository.selectOrganizerByUsername(username);
            Admin admin = adminRepository.selectAdminByUsername(username);
            if (employee != null)
                return employee;
            else if (employer != null)
                return employer;
            else if (organizer != null)
                return organizer;
            else if (admin != null)
                return admin;
            else
                return null;
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("username"));
    }

    public Person getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.selectEmployeeByUsername(authentication.getName());
        Employer employer = employerRepository.selectEmployerByUsername(authentication.getName());
        Organizer organizer = organizerRepository.selectOrganizerByUsername(authentication.getName());
        Admin admin = adminRepository.selectAdminByUsername(authentication.getName());

        if (employee != null)
            return employee;
        else if (employer != null)
            return employer;
        else if (organizer != null)
            return organizer;
        else if (admin != null)
            return admin;
        else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("username"));
    }

    public VerificationToken getVerificationToken(String verificationToken) {
        if(verificationToken != null)
            return verificationTokenRepository.findByToken(verificationToken);
        else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("VerificationToken"));
    }

    public Person registerNewUserAccount(RegisterData userDto) throws UserAlreadyExistException {
        if (userDto != null && !emailExist(userDto.getUsername())) {
            if(userDto.getRole().equals(EMPLOYEE)) {
                Employee newEmployee = Employee.builder()
                        .email(userDto.getUsername())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .locked(true)
                        .userRole(ApplicationUserRole.EMPLOYEE.getUserRole()).build();
                employeeRepository.save(newEmployee);
                return newEmployee;
            }else if(userDto.getRole().equals(EMPLOYER)) {
                Employer newEmployer = Employer.builder()
                        .email(userDto.getUsername())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .locked(true)
                        .userRole(ApplicationUserRole.EMPLOYER.getUserRole()).build();
                employerRepository.save(newEmployer);
                return newEmployer;
            }else if(userDto.getRole().equals(ORGANIZER)) {
                Organizer newOrganizer = Organizer.builder()
                        .email(userDto.getUsername())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .locked(true)
                        .userRole(ApplicationUserRole.ORGANIZER.getUserRole()).build();
                organizerRepository.save(newOrganizer);
                return newOrganizer;
            }
        } else
            throw new UserAlreadyExistException(ExceptionMessages.userAlreadyExists(userDto.getUsername()));

        return null;
    }

    private boolean emailExist(String email) {
        if(email != null)
            return employeeRepository.selectEmployeeByUsername(email) != null || employerRepository.selectEmployerByUsername(email) != null || organizerRepository.selectOrganizerByUsername(email) != null ;
        else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("VerificationToken"));
    }

    public void unlockEmployee(Employee employee) {
        if(employee != null) {
            Employee savedEmployee = employeeRepository.selectEmployeeByUsername(employee.getEmail());
            savedEmployee.setUserRole(new SimpleGrantedAuthority(ROLE_EMPLOYEE_ENABLED));
            savedEmployee.setLocked(false);
            employeeRepository.save(savedEmployee);
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("Employee"));
    }

    public void unlockEmployer(Employer employer) {
        if(employer != null) {
            Employer savedEmployer = employerRepository.selectEmployerByUsername(employer.getEmail());
            savedEmployer.setUserRole(new SimpleGrantedAuthority(ROLE_EMPLOYER_ENABLED));
            savedEmployer.setLocked(false);
            employerRepository.save(savedEmployer);
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("Employer"));
    }

    public void unlockOrganizer(Organizer organizer) {
        if(organizer != null) {
            Organizer savedOrganizer = organizerRepository.selectOrganizerByUsername(organizer.getEmail());
            savedOrganizer.setUserRole(new SimpleGrantedAuthority(ROLE_ORGANIZER_ENABLED));
            savedOrganizer.setLocked(false);
            organizerRepository.save(savedOrganizer);
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("Organizer"));
    }

    public void createVerificationToken(Person person, String token) {
        if(person != null) {
            VerificationToken myToken = VerificationToken.builder().token(token).person(person).build();
            verificationTokenRepository.save(myToken);
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("Person"));
    }

    public void createPasswordResetTokenForUser(Person person, String token) {
        if(person != null) {
            PasswordResetToken resetToken = PasswordResetToken.builder().token(token).person(person).build();
            passwordResetTokenService.save(resetToken);
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("Person"));
    }

    public void sendResetToken(Person person, String contextPath) {
        if(person != null) {
            String token = UUID.randomUUID().toString();
            createPasswordResetTokenForUser(person, token);

            String recipientAddress = person.getEmail();
            String subject = resetPasswordEmailSubject(recipientAddress);
            String confirmationUrl = contextPath + CHANGE_PASSWORD_URL + token;
            String text = resetPasswordEmailText() + BACKEND_HOST_AZURE + confirmationUrl;

            emailService.sendHtmlMessageWithTemplate(recipientAddress, subject, text);
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("Person"));

    }

    public boolean isValidPasswordResetToken(String token) {
        if(token != null) {
            PasswordResetToken passToken = passwordResetTokenService.findByToken(token);
            return isTokenFound(passToken) && (!isTokenExpired(passToken));
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("PasswordResetToken"));
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        if(passToken != null) {
            final Calendar cal = Calendar.getInstance();
            return passToken.getExpiryDate().before(cal.getTime());
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("PasswordResetToken"));
    }

    public Person getUserByPasswordResetToken(String token) {
        if(token != null) {
            Optional<Employee> employee = employeeService.getEmployeeByToken(token);
            Optional<Employer> employer = employerService.getEmployerByToken(token);
            Optional<Organizer> organizer = organizerService.getOrganizerByToken(token);
            if (employee.isPresent())
                return employee.get();
            else if (employer.isPresent())
                return employer.get();
            else if (organizer.isPresent())
                return organizer.get();
            else
                throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound("PasswordResetToken", "token", token));
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("PasswordResetToken"));
    }

    public void changeUserPassword(Person person, String password) {
        if(person != null && password != null) {
            if (person.getUserRole().equals(ApplicationUserRole.EMPLOYEE_ENABLED.getUserRole())) {
                employeeService.updatePassword((Employee) person, password);
            } else if (person.getUserRole().equals(ApplicationUserRole.EMPLOYER_ENABLED.getUserRole())) {
                employerService.updatePassword((Employer) person, password);
            } else if (person.getUserRole().equals(ApplicationUserRole.ORGANIZER_ENABLED.getUserRole()))
                organizerService.updatePassword((Organizer) person, password);
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("Person"));
    }

    public Person registerLinkedinUser(JsonNode jsonNode) {
        if(jsonNode != null) {
            Employee newEmployee = Employee.builder()
                    .email(jsonNode.get("email").asText())
                    .locked(false)
                    .userRole(ApplicationUserRole.EMPLOYEE_ENABLED.getUserRole()).build();
            employeeRepository.save(newEmployee);
            return newEmployee;
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("User"));
    }

    public String loginLinkedinUser(String email) {
        if(email == null || email.equals(""))
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("Email"));

        Employee employee = (Employee)loadUserByUsername(email);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, LINKEDIN_USER_PASSWORD, employee.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", auth.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();

        return token;
    }
}
