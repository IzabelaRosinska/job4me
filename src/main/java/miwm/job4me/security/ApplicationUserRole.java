package miwm.job4me.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ApplicationUserRole {
    EMPLOYEE("ROLE_EMPLOYEE"),
    EMPLOYEE_ENABLED("ROLE_EMPLOYEE_ENABLED"),
    EMPLOYER("ROLE_EMPLOYER"),
    EMPLOYER_ENABLED("ROLE_EMPLOYER_ENABLED"),
    ORGANIZER("ROLE_ORGANIZER"),
    ORGANIZER_ENABLED("ROLE_ORGANIZER_ENABLED"),
    ADMIN("ROLE_ADMIN");

    String userRole;

    ApplicationUserRole(String userRole) {
        this.userRole = userRole;
    }

    public SimpleGrantedAuthority getUserRole() {
        return new SimpleGrantedAuthority(userRole);
    }

}
