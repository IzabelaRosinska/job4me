package miwm.job4me.services.users;

import miwm.job4me.exceptions.AuthException;
import miwm.job4me.exceptions.UserAlreadyExistException;
import miwm.job4me.messages.UserMessages;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Person;
import miwm.job4me.model.users.UserDto;
import miwm.job4me.repositories.EmployeeRepository;
import miwm.job4me.security.ApplicationUserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserAuthenticationService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    public UserAuthenticationService(EmployeeRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
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

    public void registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if (!emailExist(userDto.getEmail())) {
           if(userDto.getRole().equals("EMPLOYEE")) {
               Employee newEmployee = Employee.builder()
                       .name(userDto.getName())
                       .email(userDto.getEmail())
                       .password(passwordEncoder.encode(userDto.getPassword()))
                       .userRole(ApplicationUserRole.EMPLOYEE.getUserRole()).build();
               employeeRepository.save(newEmployee);
           }
        }else
            throw new UserAlreadyExistException(UserMessages.ACCOUNT_FOR_EMAIL_EXISTS + userDto.getEmail());
    }

    private boolean emailExist(String email) {
        return employeeRepository.selectEmployeeByUsername(email) != null;
    }

}
