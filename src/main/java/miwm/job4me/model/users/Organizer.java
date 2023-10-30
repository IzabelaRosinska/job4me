package miwm.job4me.model.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

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
    private String organizerName;

    @Lob
    @Column(name = "description", length = 500)
    private String description;

    public String toString(){
        return getUsername();
    }
}
