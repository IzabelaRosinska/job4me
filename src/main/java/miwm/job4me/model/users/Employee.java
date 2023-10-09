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
    public Employee(Long id, String name, String telephone, String email, String password, SimpleGrantedAuthority userRole) {
        super(id, name, telephone, email, password, userRole);
    }

    public String toString(){
        return getName();
    }
}
