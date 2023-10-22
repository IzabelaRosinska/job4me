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
    private final IdValidator idValidator;
    private final EmployeeValidator employeeValidator;
    private final EmployeeMapper employeeMapper;
    private final String entityName = "Employee";


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EducationService educationService, ExperienceService experienceService, ProjectService projectService, SkillService skillService, IdValidator idValidator, EmployeeValidator employeeValidator, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.educationService = educationService;
        this.experienceService = experienceService;
        this.projectService = projectService;
        this.skillService = skillService;
        this.idValidator = idValidator;
        this.employeeValidator = employeeValidator;
        this.employeeMapper = employeeMapper;
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
        Employee employee = employeeMapper.toEntity(employeeDto);

        educationService.deleteAllByEmployeeId(employee.getId());
        experienceService.deleteAllByEmployeeId(employee.getId());
        projectService.deleteAllByEmployeeId(employee.getId());
        skillService.deleteAllByEmployeeId(employee.getId());

        for (Education education : employee.getEducation()) {
            education.setEmployee(employee);
            educationService.save(education);
        }

        for (Experience experience : employee.getExperience()) {
            experience.setEmployee(employee);
            experienceService.save(experience);
        }

        for (Project project : employee.getProjects()) {
            project.setEmployee(employee);
            projectService.save(project);
        }

        for (Skill skill : employee.getSkills()) {
            skill.setEmployee(employee);
            skillService.save(skill);
        }

        employeeRepository.save(employee);

        return employeeMapper.toDto(employee);
    }

}
