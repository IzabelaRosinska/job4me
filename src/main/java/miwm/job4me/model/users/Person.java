package miwm.job4me.model.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.validators.ValidEmail;
import miwm.job4me.validators.ValidName;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Person extends BaseEntity implements UserDetails {

    public Person(Long id, String name, String telephone, String email, String password, SimpleGrantedAuthority userRole) {
        super(id);
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    @ValidName
    @NotBlank
    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "telephone")
    private String telephone;

    @ValidEmail
    @NotEmpty
    @Column(name = "email")
    private String email;

    @NotBlank
    @NotEmpty
    @Column(name = "password")
    private String password;

    @Column(name = "locked")
    private boolean locked;

    @NotNull
    @Column(name = "role")
    private SimpleGrantedAuthority userRole;


    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>(0);
        list.add(userRole);
        return list;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}