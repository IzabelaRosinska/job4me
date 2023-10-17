package miwm.job4me.model.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee extends Person {

    @Builder
    public Employee(Long id, String telephone, String email, String password, SimpleGrantedAuthority userRole) {
        super(id, telephone, email, password, userRole);
    }

//    @ValidName
//    @NotBlank
//    @NotEmpty
//    @Column(name = "first_name")
//    private String firstName;
//
//    @ValidName
//    @NotBlank
//    @NotEmpty
//    @Column(name = "last_name")
//    private String lastName;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
//    private ArrayList<Education> education;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
//    private ArrayList<Experience> experience;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
//    private ArrayList<Skill> skills;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
//    private ArrayList<Project> projects;
//
//    @Column(name = "about_me")
//    private String aboutMe;
//
//    @Column(name = "interests")
//    private String interests;

    public String toString() {
        return getUsername();
    }
}
