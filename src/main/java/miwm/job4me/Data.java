package miwm.job4me;

import lombok.extern.slf4j.Slf4j;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.EmployeeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class Data implements ApplicationListener<ContextRefreshedEvent> {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public Data(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        employeeRepository.saveAll(getEmployees());
    }

    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        Employee user = Employee.builder().id(1l).name("userTest").email("userTest@wp.pl").password(passwordEncoder.encode("userTest")).userRole(new SimpleGrantedAuthority("ROLE_EMPLOYEE")).build();
        employees.add(user);
        return employees;
    }

}