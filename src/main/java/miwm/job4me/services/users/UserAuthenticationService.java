package miwm.job4me.services.users;

import miwm.job4me.exceptions.AuthException;
import miwm.job4me.exceptions.UserAlreadyExistException;
import miwm.job4me.messages.UserMessages;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.model.users.Person;
import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.repositories.users.EmployerRepository;
import miwm.job4me.repositories.users.OrganizerRepository;
import miwm.job4me.security.ApplicationUserRole;
import miwm.job4me.web.model.users.RegisterData;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserAuthenticationService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final EmployerRepository employerRepository;
    private final OrganizerRepository organizerRepository;

    public UserAuthenticationService(EmployeeRepository clientRepository, PasswordEncoder passwordEncoder, EmployerRepository employerRepository, OrganizerRepository organizerRepository) {
        this.employeeRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.employerRepository = employerRepository;
        this.organizerRepository = organizerRepository;
    }

    @Override
    public Person loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.selectEmployeeByUsername(username);
    }

    public Long getAuthenticatedEmployeeId() throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.selectEmployeeByUsername(authentication.getName());
        if(employee == null)
            throw new AuthException(UserMessages.BANNED_RESOURCES);
        return employee.getId();
    }

    public Person getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.selectEmployeeByUsername(authentication.getName());
        return employee;
    }

    public Person registerNewUserAccount(RegisterData userDto) throws UserAlreadyExistException {
        if (!emailExist(userDto.getEmail())) {
            if(userDto.getRole().equals("EMPLOYEE")) {
                Employee newEmployee = Employee.builder()
                        .email(userDto.getEmail())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .userRole(ApplicationUserRole.EMPLOYEE.getUserRole()).build();
                employeeRepository.save(newEmployee);
                return newEmployee;
            }else if(userDto.getRole().equals("EMPLOYER")) {
                Employer newEmployer = Employer.builder()
                        .email(userDto.getEmail())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .userRole(ApplicationUserRole.EMPLOYER.getUserRole()).build();
                employerRepository.save(newEmployer);
                return newEmployer;
            }else if(userDto.getRole().equals("ORGANIZER")) {
                Organizer newOrganizer = Organizer.builder()
                        .email(userDto.getEmail())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .userRole(ApplicationUserRole.ORGANIZER.getUserRole()).build();
                organizerRepository.save(newOrganizer);
                return newOrganizer;
            }
        }else
            throw new UserAlreadyExistException(UserMessages.ACCOUNT_FOR_EMAIL_EXISTS + userDto.getEmail());
        return null;
    }

    private boolean emailExist(String email) {
        return employeeRepository.selectEmployeeByUsername(email) != null;
    }

}
