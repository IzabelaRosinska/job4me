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
@Table(name = "admins")
public class Admin extends Account {

    @Builder
    public Admin(Long id, String telephone, String email, String password, boolean locked, SimpleGrantedAuthority userRole) {
        super(id, telephone, email, password, locked, userRole);
    }

    public String toString(){
        return getUsername();
    }
}
