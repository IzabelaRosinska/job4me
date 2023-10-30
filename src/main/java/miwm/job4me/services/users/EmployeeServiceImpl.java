package miwm.job4me.services.users;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Education;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.cv.Project;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.services.cv.EducationService;
import miwm.job4me.services.cv.ExperienceService;
import miwm.job4me.services.cv.ProjectService;
import miwm.job4me.services.cv.SkillService;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.validators.entity.users.EmployeeValidator;
import miwm.job4me.web.mappers.users.EmployeeMapper;
import miwm.job4me.web.model.users.EmployeeDto;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EducationService educationService;
    private final ExperienceService experienceService;
    private final ProjectService projectService;
    private final SkillService skillService;
    private final UserAuthenticationService userAuthenticationService;
    private final IdValidator idValidator;
    private final EmployeeValidator employeeValidator;
    private final EmployeeMapper employeeMapper;
    private final String entityName = "Employee";


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EducationService educationService, ExperienceService experienceService, ProjectService projectService, SkillService skillService, UserAuthenticationService userAuthenticationService, IdValidator idValidator, EmployeeValidator employeeValidator, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.educationService = educationService;
        this.experienceService = experienceService;
        this.projectService = projectService;
        this.skillService = skillService;
        this.userAuthenticationService = userAuthenticationService;
        this.idValidator = idValidator;
        this.employeeValidator = employeeValidator;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDto getEmployeeDetails() {
        Employee employee = userAuthenticationService.getAuthenticatedEmployee();
        EmployeeDto employeeDto = employeeMapper.toDto(employee);
        return employeeDto;
    }

    @Override
    @Transactional
    public EmployeeDto saveEmployeeDetails(EmployeeDto employeeDto) {
        Employee employee = userAuthenticationService.getAuthenticatedEmployee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setContactEmail(employeeDto.getEmail());
        employee.setTelephone(employeeDto.getTelephone());
        employee.setAboutMe(employeeDto.getAboutMe());
        employee.setInterests(employeeDto.getInterests());
        employee.setEducation(employeeMapper.stringListToEducationSet(employeeDto.getEducation()));
        employee.setExperience(employeeMapper.stringListToExperienceSet(employeeDto.getExperience()));
        employee.setProjects(employeeMapper.stringListToProjectsSet(employeeDto.getProjects()));
        employee.setSkills(employeeMapper.stringListToSkillsSet(employeeDto.getSkills()));
        return employeeDto;
    }

    @Override
    public Set<Employee> findAll() {
        return new HashSet<>(employeeRepository
                .findAll());
    }

    @Override
    public Employee findById(Long id) {
        idValidator.validateLongId(id, entityName);

        return employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(entityName, id)));
    }

    @Override
    public EmployeeDto findCurrentEmployee() {
        Long currentEmployeeId = userAuthenticationService.getAuthenticatedUser().getId();

        return employeeRepository
                .findById(currentEmployeeId)
                .map(employeeMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(entityName, currentEmployeeId)));
    }

    @Override
    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeValidator.validateEmployeeExistsById(employee.getId());
        employeeRepository.delete(employee);
    }

    @Override
    public void deleteById(Long id) {
        employeeValidator.validateEmployeeExistsById(id);
        employeeRepository.deleteById(id);
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
                educationService.save(education);
            }
        }

        if (employee.getExperience() != null) {
            for (Experience experience : employee.getExperience()) {
                experience.setEmployee(employee);
                experienceService.save(experience);
            }
        }

        if (employee.getProjects() != null) {
            for (Project project : employee.getProjects()) {
                project.setEmployee(employee);
                projectService.save(project);
            }
        }

        if (employee.getSkills() != null) {
            for (Skill skill : employee.getSkills()) {
                skill.setEmployee(employee);
                skillService.save(skill);
            }
        }

        Employee result = employeeRepository.save(employee);

        return employeeMapper.toDto(result);
    }

}
