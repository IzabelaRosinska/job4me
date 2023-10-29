package miwm.job4me.services;

import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.repositories.users.EmployerRepository;
import miwm.job4me.repositories.users.OrganizerRepository;
import miwm.job4me.services.cv.EducationService;
import miwm.job4me.services.cv.ExperienceService;
import miwm.job4me.services.cv.ProjectService;
import miwm.job4me.services.cv.SkillService;
import miwm.job4me.services.users.EmployeeServiceImpl;
import miwm.job4me.services.users.UserAuthenticationService;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.web.mappers.users.EmployeeMapper;
import miwm.job4me.web.mappers.users.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfiguration {

    @Bean
    UserAuthenticationService userAuthenticationService(final EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, EmployerRepository employerRepository, OrganizerRepository organizerRepository) {
        return new UserAuthenticationService(employeeRepository, passwordEncoder, employerRepository, organizerRepository);
    }

    @Bean
    EmployeeServiceImpl clientServiceImpl(final EmployeeRepository employeeRepository, final EducationService educationService, final ExperienceService experienceService, final ProjectService projectService, final SkillService skillService, UserAuthenticationService userAuthService, final IdValidator idValidator, final EmployeeValidator employeeValidator, final EmployeeMapper employeeMapper) {
        return new EmployeeServiceImpl(employeeRepository, educationService, experienceService, projectService, skillService, userAuthService, idValidator, employeeValidator, employeeMapper);
    }

    @Bean
    UserMapper userMapper() {
        return new UserMapper();
    }

}
