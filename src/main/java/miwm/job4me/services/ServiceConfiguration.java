package miwm.job4me.services;

import miwm.job4me.repositories.EmployeeRepository;
import miwm.job4me.services.users.EmployeeServiceImpl;
import miwm.job4me.services.users.UserAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfiguration {

    @Bean
    UserAuthenticationService userAuthenticationService(final EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        return new UserAuthenticationService(employeeRepository, passwordEncoder);
    }

    @Bean
    EmployeeServiceImpl clientServiceImpl(final EmployeeRepository employeeRepository) {
        return new EmployeeServiceImpl(employeeRepository);
    }

}
