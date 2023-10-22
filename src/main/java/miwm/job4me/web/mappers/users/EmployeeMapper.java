package miwm.job4me.web.mappers.users;

import miwm.job4me.model.cv.Education;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.cv.Project;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.model.users.Employee;
import miwm.job4me.web.model.users.EmployeeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class EmployeeMapper {
    public EmployeeDto toDto(Employee employee) {
        System.out.println("<3");
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getContactEmail());
        employeeDto.setTelephone(employee.getTelephone());
        employeeDto.setEducation(educationSetToStringList(employee.getEducation()));
        employeeDto.setExperience(experienceSetToStringList(employee.getExperience()));
        employeeDto.setProjects(projectsSetToStringList(employee.getProjects()));
        employeeDto.setSkills(skillsSetToStringList(employee.getSkills()));
        employeeDto.setAboutMe(employee.getAboutMe());
        employeeDto.setInterests(employee.getInterests());
        System.out.println("<3");
        return employeeDto;
    }

    public Employee toEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setContactEmail(employeeDto.getEmail());
        employee.setTelephone(employeeDto.getTelephone());
        employee.setEducation(stringListToEducationSet(employeeDto.getEducation()));
        employee.setExperience(stringListToExperienceSet(employeeDto.getExperience()));
        employee.setProjects(stringListToProjectsSet(employeeDto.getProjects()));
        employee.setSkills(stringListToSkillsSet(employeeDto.getSkills()));
        employee.setAboutMe(employeeDto.getAboutMe());
        employee.setInterests(employeeDto.getInterests());
        return employee;
    }

    private ArrayList<String> educationSetToStringList(Set<Education> education) {
        ArrayList<String> educationList = new ArrayList<>();

        if (education != null) {
            for (Education e : education) {
                educationList.add(e.getDescription());
            }
        }

        return educationList;
    }

    private ArrayList<String> experienceSetToStringList(Set<Experience> experience) {
        ArrayList<String> experienceList = new ArrayList<>();

        if (experience != null) {
            for (Experience e : experience) {
                experienceList.add(e.getDescription());
            }
        }

        return experienceList;
    }

    private ArrayList<String> projectsSetToStringList(Set<Project> projects) {
        ArrayList<String> projectsList = new ArrayList<>();

        if (projects != null) {
            for (Project p : projects) {
                projectsList.add(p.getDescription());
            }
        }

        return projectsList;
    }

    private ArrayList<String> skillsSetToStringList(Set<Skill> skills) {
        ArrayList<String> skillsList = new ArrayList<>();

        if (skills != null) {
            for (Skill s : skills) {
                skillsList.add(s.getDescription());
            }
        }

        return skillsList;
    }

    private Set<Education> stringListToEducationSet(ArrayList<String> education) {
        Set<Education> educationSet = new HashSet<>();

        if (education != null) {
            for (String e : education) {
                Education educationEntity = Education.builder().description(e).build();
                educationSet.add(educationEntity);
            }
        }

        return educationSet;
    }

    private Set<Experience> stringListToExperienceSet(ArrayList<String> experience) {
        Set<Experience> experienceSet = new HashSet<>();

        if (experience != null) {
            for (String e : experience) {
                Experience experienceEntity = Experience.builder().description(e).build();
                experienceSet.add(experienceEntity);
            }
        }

        return experienceSet;
    }

    private Set<Project> stringListToProjectsSet(ArrayList<String> projects) {
        Set<Project> projectsSet = new HashSet<>();

        if (projects != null) {
            for (String p : projects) {
                Project projectEntity = Project.builder().description(p).build();
                projectsSet.add(projectEntity);
            }
        }

        return projectsSet;
    }

    private Set<Skill> stringListToSkillsSet(ArrayList<String> skills) {
        Set<Skill> skillsSet = new HashSet<>();

        if (skills != null) {
            for (String s : skills) {
                Skill skillEntity = Skill.builder().description(s).build();
                skillsSet.add(skillEntity);
            }
        }

        return skillsSet;
    }

}
