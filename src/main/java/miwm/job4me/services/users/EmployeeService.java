package miwm.job4me.services.users;

import com.fasterxml.jackson.databind.JsonNode;
import miwm.job4me.model.cv.Education;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.cv.Project;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Person;
import miwm.job4me.services.BaseService;
import miwm.job4me.web.model.cv.EducationDto;
import miwm.job4me.web.model.cv.ExperienceDto;
import miwm.job4me.web.model.cv.ProjectDto;
import miwm.job4me.web.model.cv.SkillDto;
import miwm.job4me.web.model.users.EmployeeDto;
import miwm.job4me.web.model.users.EmployerDto;

public interface EmployeeService extends BaseService<Employee, Long> {
    void strictExistsById(Long id);

    boolean existsById(Long id);

    EmployeeDto updateCV(EmployeeDto employeeDto);

    EmployeeDto getEmployeeDetails();

    EmployeeDto saveEmployeeDetails(EmployeeDto employeeDto);

    EmployeeDto findCurrentEmployee();

    void saveEmployeeDataFromLinkedin(Person user, JsonNode jsonNode);

    EducationDto saveEducation(Education education);

    ExperienceDto saveExperience(Experience experience);

    ProjectDto saveProject(Project project);

    SkillDto saveSkill(Skill skill);

    EmployeeDto findEmployeeById(Long id);

    void addEmployerToSaved(Long id);
    void deleteEmployerFromSaved(Long id);
}
