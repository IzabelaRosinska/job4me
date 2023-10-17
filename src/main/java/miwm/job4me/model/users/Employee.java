package miwm.job4me.model.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.cv.Education;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.cv.Project;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.validators.ValidName;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
    public Employee(Long id, String telephone, String email, String password, SimpleGrantedAuthority userRole,
                    String firstName, String lastName, Set<Education> education, Set<Experience> experience,
                    Set<Skill> skills, Set<Project> projects, String aboutMe, String interests) {
        super(id, telephone, email, password, userRole);
        this.firstName = firstName;
        this.lastName = lastName;
        this.education = education;
        this.experience = experience;
        this.skills = skills;
        this.projects = projects;
        this.aboutMe = aboutMe;
        this.interests = interests;
    }

    @ValidName
    @NotBlank
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @ValidName
    @NotBlank
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

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

    @Lob
    @Length(max = 1000)
    @Column(name = "about_me")
    private String aboutMe;

    @Lob
    @Length(max = 1000)
    @Column(name = "interests")
    private String interests;

    public String toString() {
        return getUsername();
    }
}
