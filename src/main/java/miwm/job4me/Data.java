package miwm.job4me;

import lombok.extern.slf4j.Slf4j;
import miwm.job4me.model.cv.Education;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.cv.Project;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.repositories.users.EmployerRepository;
import miwm.job4me.repositories.users.OrganizerRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Slf4j
@Component
public class Data implements ApplicationListener<ContextRefreshedEvent> {

    private final EmployeeRepository employeeRepository;
    private final EmployerRepository employerRepository;
    private final OrganizerRepository organizerRepository;
    private final PasswordEncoder passwordEncoder;

    public Data(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, EmployerRepository employerRepository, OrganizerRepository organizerRepository) {
        this.employeeRepository = employeeRepository;
        this.employerRepository = employerRepository;
        this.organizerRepository = organizerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
      // employeeRepository.saveAll(getEmployees());
      // employerRepository.saveAll(getEmployers());
      // organizerRepository.saveAll(getOrganizer());
    }

    public List<Employer> getEmployers() {
        List<Employer> employers = new ArrayList<>();
        Employer employer = Employer.builder().email("employer@wp.pl").password(passwordEncoder.encode("12345")).companyName("EMP").locked(false).userRole(new SimpleGrantedAuthority("ROLE_EMPLOYER_ENABLED")).build();
        employers.add(employer);
        return employers;
    }

    public List<Organizer> getOrganizer() {
        List<Organizer> organizers = new ArrayList<>();
        Organizer organizer = Organizer.builder().email("organizer@wp.pl").password(passwordEncoder.encode("12345")).locked(false).userRole(new SimpleGrantedAuthority("ROLE_ORGANIZER_ENABLED")).build();
        organizers.add(organizer);
        return organizers;
    }

    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        Employee user = Employee.builder()
                .id(1l)
                .email("userTest@wp.pl")
                .password(passwordEncoder.encode("userTest"))
                .userRole(new SimpleGrantedAuthority("ROLE_EMPLOYEE_ENABLED"))
                .firstName("Jan")
                .lastName("Kowalski")
                .telephone("+48 123456789")
                .contactEmail("jan.kowalski@gmail.com")
                .aboutMe("Jestem studentem informatyki na Politechnice Wrocławskiej. Interesuje się programowaniem w Javie.")
                .interests("Programowanie, sport, muzyka")
                .build();

        Set<Education> educations = Set.of(
                Education.builder()
                        .description("Studia inżynierskie")
                        .employee(user)
                        .build(),
                Education.builder()
                        .description("Studia magisterskie")
                        .employee(user)
                        .build()
        );

        user.setEducation(educations);

        Set<Experience> experiences = Set.of(
                Experience.builder()
                        .description("Praktyki w firmie XYZ")
                        .employee(user)
                        .build(),
                Experience.builder()
                        .description("Praktyki w firmie ABC")
                        .employee(user)
                        .build()
        );

        user.setExperience(experiences);

        Set<Project> projects = Set.of(
                Project.builder()
                        .description("Projekt 1")
                        .employee(user)
                        .build(),
                Project.builder()
                        .description("Projekt 2")
                        .employee(user)
                        .build()
        );

        user.setProjects(projects);

        employees.add(user);


        return employees;
    }
}
