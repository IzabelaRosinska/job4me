package miwm.job4me.model.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.event.JobFair;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "organizers")
public class Organizer extends Person {

    @Builder
    public Organizer(Long id, String telephone, String email, String password, boolean locked, SimpleGrantedAuthority userRole) {
        super(id, telephone, email, password, locked, userRole);
    }

    @Column(name = "organizer_name", length = 100)
    private String name;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(name = "telephone", length = 20)
    private String telephone;

    @Lob
    @Column(name = "description", length = 500)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizer")
    private Set<JobFair> fairs = new HashSet<>();

    public String toString() {
        return getUsername();
    }
}
