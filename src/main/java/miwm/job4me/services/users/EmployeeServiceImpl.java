package miwm.job4me.services.users;

import com.fasterxml.jackson.databind.JsonNode;
import miwm.job4me.exceptions.AuthException;
import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Education;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.cv.Project;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Person;
import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.services.cv.EducationService;
import miwm.job4me.services.cv.ExperienceService;
import miwm.job4me.services.cv.ProjectService;
import miwm.job4me.services.cv.SkillService;
import miwm.job4me.services.recommendation.RecommendationNotifierService;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.users.EmployeeMapper;
import miwm.job4me.web.model.cv.EducationDto;
import miwm.job4me.web.model.cv.ExperienceDto;
import miwm.job4me.web.model.cv.ProjectDto;
import miwm.job4me.web.model.cv.SkillDto;
import miwm.job4me.web.model.users.EmployeeDto;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EducationService educationService;
    private final ExperienceService experienceService;
    private final ProjectService projectService;
    private final SkillService skillService;
    private final RecommendationNotifierService recommendationNotifierService;
    private final IdValidator idValidator;
    private final EmployeeValidator employeeValidator;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;
    private final String ENTITY_NAME = "Employee";

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EducationService educationService, ExperienceService experienceService, ProjectService projectService, SkillService skillService, RecommendationNotifierService recommendationNotifierService, IdValidator idValidator, EmployeeValidator employeeValidator, EmployeeMapper employeeMapper, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.educationService = educationService;
        this.experienceService = experienceService;
        this.projectService = projectService;
        this.skillService = skillService;
        this.recommendationNotifierService = recommendationNotifierService;
        this.idValidator = idValidator;
        this.employeeValidator = employeeValidator;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public EmployeeDto getEmployeeDetails() {
        Employee employee = getAuthEmployee();
        EmployeeDto employeeDto = employeeMapper.toDto(employee);
        return employeeDto;
    }

    @Override
    @Transactional
    public EmployeeDto saveEmployeeDetails(EmployeeDto employeeDto) {
        Employee employee = getAuthEmployee();
        if (employeeDto != null) {
            employee = employeeMapper.employeeDtotoSavedEmployee(employee, employeeDto);
            save(employee);
            return employeeDto;
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    public Set<Employee> findAll() {
        return new HashSet<>(employeeRepository
                .findAll());
    }

    @Override
    public Employee findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);

        return employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public EmployeeDto findCurrentEmployee() {
        Long currentEmployeeId = getAuthEmployee().getId();

        return employeeRepository
                .findById(currentEmployeeId)
                .map(employeeMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, currentEmployeeId)));
    }

    @Override
    public EducationDto saveEducation(Education education) {
        if (education == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
        strictExistsById(education.getEmployee().getId());
        return educationService.save(education);
    }

    @Override
    public ExperienceDto saveExperience(Experience experience) {
        if (experience == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
        strictExistsById(experience.getEmployee().getId());
        return experienceService.save(experience);
    }

    @Override
    public ProjectDto saveProject(Project project) {
        if (project == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
        strictExistsById(project.getEmployee().getId());
        return projectService.save(project);
    }

    @Override
    public SkillDto saveSkill(Skill skill) {
        if (skill == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
        strictExistsById(skill.getEmployee().getId());
        return skillService.save(skill);
    }

    @Override
    public Optional<Employee> getEmployeeByToken(String token) {
        if (token != null) {
            Optional<Employee> employee = employeeRepository.getEmployeeByToken(token);
            if (employee.isPresent())
                return employee;
            else
                throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, "token", token));
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    public void updatePassword(Employee employee, @Length(min = 5, max = 15) String password) {
        if (employee != null) {
            employee.setPassword(passwordEncoder.encode(password));
            save(employee);
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    public EmployeeDto findEmployeeById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent())
            return employeeMapper.toDto(employee.get());
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
    }

    @Override
    @Transactional
    public void saveEmployeeDataFromLinkedin(Person user, JsonNode jsonNode) {
        if (user != null && jsonNode != null) {
            Employee employee = employeeRepository.selectEmployeeByUsername(user.getUsername());
            String username = jsonNode.get("given_name").asText();
            String familyName = jsonNode.get("family_name").asText();
            employee.setFirstName(username);
            employee.setLastName(familyName);
            employeeRepository.save(employee);
            recommendationNotifierService.notifyUpdatedEmployee(employee.getId());
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    @Transactional
    public Employee save(Employee employee) {
        if (employee == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        recommendationNotifierService.notifyUpdatedEmployee(employee.getId());
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Employee employee) {
        strictExistsById(employee.getId());
        employeeRepository.delete(employee);
        recommendationNotifierService.notifyRemovedEmployee(employee.getId());
    }

    @Override
    public void deleteById(Long id) {
        strictExistsById(id);
        employeeRepository.deleteById(id);
        recommendationNotifierService.notifyRemovedEmployee(id);
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);

        return employeeRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    @Transactional
    public EmployeeDto updateCV(EmployeeDto employeeDto) {
        employeeValidator.validateForUpdateDto(employeeDto);
        Employee oldEmployee = findById(employeeDto.getId());

        Employee employee = employeeMapper.toEntity(employeeDto);
        employee.setEmail(oldEmployee.getEmail());
        employee.setPassword(oldEmployee.getPassword());
        employee.setUserRole(oldEmployee.getUserRole());
        employee.setLocked(oldEmployee.isLocked());

        educationService.deleteAllByEmployeeId(employee.getId());
        experienceService.deleteAllByEmployeeId(employee.getId());
        projectService.deleteAllByEmployeeId(employee.getId());
        skillService.deleteAllByEmployeeId(employee.getId());

        if (employee.getEducation() != null) {
            for (Education education : employee.getEducation()) {
                education.setEmployee(employee);
                saveEducation(education);
            }
        }

        if (employee.getExperience() != null) {
            for (Experience experience : employee.getExperience()) {
                experience.setEmployee(employee);
                saveExperience(experience);
            }
        }

        if (employee.getProjects() != null) {
            for (Project project : employee.getProjects()) {
                project.setEmployee(employee);
                saveProject(project);
            }
        }

        if (employee.getSkills() != null) {
            for (Skill skill : employee.getSkills()) {
                skill.setEmployee(employee);
                saveSkill(skill);
            }
        }

        employee.setIsEmbeddingCurrent(false);
        Employee result = employeeRepository.save(employee);
        recommendationNotifierService.notifyUpdatedEmployee(result.getId());

        return employeeMapper.toDto(result);
    }

    @Override
    public Employee getAuthEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.selectEmployeeByUsername(authentication.getName());

        if (employee == null) {
            throw new AuthException(ExceptionMessages.unauthorized(ENTITY_NAME));
        }

        return employee;
    }
}
