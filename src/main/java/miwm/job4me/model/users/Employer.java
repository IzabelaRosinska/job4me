package miwm.job4me.model.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employers")
public class Employer extends Person {

    @Builder
    public Employer(Long id, String telephone, String email, String password, boolean locked, SimpleGrantedAuthority userRole,
                    String companyName, String contactEmail, String description, String displayDescription, String photo, String address) {
        super(id, telephone, email, password, locked, userRole);
        this.companyName = companyName;
        this.contactEmail = contactEmail;
        this.description = description;
        this.displayDescription = displayDescription;
        this.photo = photo;
        this.address = address;
    }

    @Column(name = "company_name", length = 100)
    private String companyName;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Lob
    @Column(name = "description", length = 500)
    private String description;

    @Lob
    @Column(name = "display_description", length = 1000)
    private String displayDescription;

    @Column(name = "photo", length = 13000)
    private String photo;

    @Column(name = "address", length = 100)
    private String address;

    @Size(max = 20)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employer")
    private Set<SavedEmployee> savedEmployees = new HashSet<>();

    public String toString() {
        return getUsername();
    }
}
