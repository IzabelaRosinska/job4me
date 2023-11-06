package miwm.job4me.services;

import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.repositories.users.EmployerRepository;
import miwm.job4me.repositories.users.OrganizerRepository;
import miwm.job4me.services.cv.EducationService;
import miwm.job4me.services.cv.ExperienceService;
import miwm.job4me.services.cv.ProjectService;
import miwm.job4me.services.cv.SkillService;
import miwm.job4me.services.users.EmployeeServiceImpl;
import miwm.job4me.services.users.EmployerServiceImpl;
import miwm.job4me.services.users.OrganizerServiceImpl;
import miwm.job4me.services.users.UserAuthenticationService;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.users.EmployeeMapper;
import miwm.job4me.web.mappers.users.EmployerMapper;
import miwm.job4me.web.mappers.users.OrganizerMapper;
import miwm.job4me.web.mappers.users.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    EmployeeServiceImpl clientServiceImpl(final EmployeeRepository employeeRepository, final EducationService educationService, final ExperienceService experienceService, final ProjectService projectService, final SkillService skillService, UserAuthenticationService userAuthenticationService, final IdValidator idValidator, final EmployeeValidator employeeValidator, final EmployeeMapper employeeMapper) {
        return new EmployeeServiceImpl(employeeRepository, educationService, experienceService, projectService, skillService, userAuthenticationService, idValidator, employeeValidator, employeeMapper);
    }

    @Bean
    EmployerServiceImpl employerServiceImpl(final UserAuthenticationService userAuthService, final EmployerMapper employerMapper, final EmployerRepository employerRepository) {
        return new EmployerServiceImpl(userAuthService, employerMapper, employerRepository);
    }

    @Bean
    UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    OrganizerServiceImpl organizerServiceImpl(UserAuthenticationService userAuthenticationService, OrganizerMapper organizerMapper, OrganizerRepository organizerRepository) {
        return new OrganizerServiceImpl(userAuthenticationService, organizerMapper, organizerRepository);
    }

}
