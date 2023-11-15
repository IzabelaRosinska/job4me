package miwm.job4me.web.mappers.users;

import miwm.job4me.model.users.Employee;
import miwm.job4me.services.users.SavedEmployeeService;
import miwm.job4me.web.model.users.EmployeeReviewDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeeReviewMapper {

    private final EmployeeMapper employeeMapper;

    public EmployeeReviewMapper(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    public EmployeeReviewDto toDto(Employee employee, Boolean isSaved) {
        EmployeeReviewDto employeeReviewDto = new EmployeeReviewDto();
        employeeReviewDto.setId(employee.getId());
        employeeReviewDto.setFirstName(employee.getFirstName());
        employeeReviewDto.setLastName(employee.getLastName());
        employeeReviewDto.setEmail(employee.getContactEmail());
        employeeReviewDto.setTelephone(employee.getTelephone());
        employeeReviewDto.setIsSaved(isSaved);
        employeeReviewDto.setEducation(employeeMapper.educationSetToStringList(employee.getEducation()));
        employeeReviewDto.setExperience(employeeMapper.experienceSetToStringList(employee.getExperience()));
        employeeReviewDto.setProjects(employeeMapper.projectsSetToStringList(employee.getProjects()));
        employeeReviewDto.setSkills(employeeMapper.skillsSetToStringList(employee.getSkills()));
        employeeReviewDto.setAboutMe(employee.getAboutMe());
        employeeReviewDto.setInterests(employee.getInterests());
        return employeeReviewDto;
    }

    public Employee toEntity(EmployeeReviewDto employeeReviewDto) {
        Employee employee = new Employee();
        employee.setId(employeeReviewDto.getId());
        employee.setFirstName(employeeReviewDto.getFirstName());
        employee.setLastName(employeeReviewDto.getLastName());
        employee.setContactEmail(employeeReviewDto.getEmail());
        employee.setTelephone(employeeReviewDto.getTelephone());
        employee.setEducation(employeeMapper.stringListToEducationSet(employeeReviewDto.getEducation()));
        employee.setExperience(employeeMapper.stringListToExperienceSet(employeeReviewDto.getExperience()));
        employee.setProjects(employeeMapper.stringListToProjectsSet(employeeReviewDto.getProjects()));
        employee.setSkills(employeeMapper.stringListToSkillsSet(employeeReviewDto.getSkills()));
        employee.setAboutMe(employeeReviewDto.getAboutMe());
        employee.setInterests(employeeReviewDto.getInterests());
        return employee;
    }
}
