package miwm.job4me.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ApplicationUserRole {
    EMPLOYEE("ROLE_EMPLOYEE"),
    EMPLOYER("ROLE_EMPLOYER"),
    ORGANIZER("ROLE_ORGANIZER"),
    ADMIN("ROLE_ADMIN");

    String userRole;

    ApplicationUserRole(String userRole) {
        this.userRole = userRole;
    }

    public SimpleGrantedAuthority getUserRole() {
        return new SimpleGrantedAuthority(userRole);
    }

}
