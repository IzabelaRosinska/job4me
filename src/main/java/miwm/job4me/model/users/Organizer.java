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
@Table(name = "organizers")
public class Organizer extends Person {

    @Builder
    public Organizer(Long id, String telephone, String email, String password, SimpleGrantedAuthority userRole) {
        super(id, telephone, email, password, userRole);
    }

    public String toString(){
        return getUsername();
    }
}
