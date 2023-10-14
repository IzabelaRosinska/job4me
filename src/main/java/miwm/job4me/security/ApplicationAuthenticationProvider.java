package miwm.job4me.security;

import miwm.job4me.messages.UserMessages;
import miwm.job4me.model.users.Person;
import miwm.job4me.services.users.UserAuthenticationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationAuthenticationProvider implements AuthenticationProvider {

    private UserAuthenticationService userAuthService;
    private PasswordEncoder passwordEncoder;

    public ApplicationAuthenticationProvider(UserAuthenticationService userAuthService, PasswordEncoder passwordEncoder) {
        this.userAuthService = userAuthService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Person user = userAuthService.loadUserByUsername(username);

        if (passwordEncoder.matches(password, user.getPassword()))
            return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
        else
            throw new BadCredentialsException(UserMessages.INVALID_PASSWORD);
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
