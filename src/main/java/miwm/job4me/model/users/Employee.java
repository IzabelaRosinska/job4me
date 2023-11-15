package miwm.job4me.model.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.cv.Education;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.cv.Project;
import miwm.job4me.model.cv.Skill;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee extends Person {

    @Builder
    public Employee(Long id, String telephone, String email, String password, boolean locked, SimpleGrantedAuthority userRole, String firstName, String lastName, String contactEmail, Set<Education> education, Set<Experience> experience, Set<Skill> skills, Set<Project> projects, String aboutMe, String interests) {
        super(id, telephone, email, password, locked, userRole);
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactEmail = contactEmail;
        this.education = education;
        this.experience = experience;
        this.skills = skills;
        this.projects = projects;
        this.aboutMe = aboutMe;
        this.interests = interests;
    }

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Size(max = 10)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<Education> education = new HashSet<>();

    @Size(max = 10)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<Experience> experience = new HashSet<>();

    @Size(max = 20)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<Skill> skills = new HashSet<>();

    @Size(max = 10)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<Project> projects = new HashSet<>();

    @Length(max = 500)
    @Column(name = "about_me", length = 500)
    private String aboutMe;

    @Length(max = 500)
    @Column(name = "interests", length = 500)
    private String interests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<SavedEmployee> savedEmployees = new HashSet<>();

    public String toString() {
        return getUsername();
    }
}
