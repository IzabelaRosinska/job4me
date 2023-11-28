package miwm.job4me.security;

import miwm.job4me.jwt.JwtConfig;
import miwm.job4me.jwt.JwtTokenVerifier;
import miwm.job4me.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import miwm.job4me.messages.AppMessages;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;

import static miwm.job4me.messages.AppMessages.*;


@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public ApplicationSecurityConfig(SecretKey secretKey, JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilterBefore(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JwtTokenVerifier(secretKey), BasicAuthenticationFilter.class)

                    .authorizeRequests()
                    .antMatchers("/employee/account").hasAnyRole(EMPLOYEE, EMPLOYEE_ENABLED)
                    .antMatchers("/employer/account").hasAnyRole(EMPLOYEE, EMPLOYER_ENABLED)
                    .antMatchers("/organizer/account").hasAnyRole(ORGANIZER, ORGANIZER_ENABLED)
                    .antMatchers("/employee/**").hasRole(EMPLOYEE_ENABLED)
                    .antMatchers("/employer/**").hasRole(EMPLOYER_ENABLED)
                    .antMatchers("/organizer/**").hasRole(ORGANIZER_ENABLED)
                    .antMatchers("/fragments/**").hasAnyRole(EMPLOYEE, EMPLOYEE, ORGANIZER)
                    .antMatchers("/home").permitAll()
                    .antMatchers("/account/**").permitAll()
                    .antMatchers("/levels").permitAll()
                    .antMatchers("/linkedin/**").permitAll()
                    .antMatchers("/auth/linkedin/callback").permitAll()
                    .antMatchers("/signup").permitAll()
                    .antMatchers("/login").permitAll()


                    .and().formLogin().loginPage(LOGIN_URL).permitAll().successHandler(appAuthenticationSuccessHandler())
                    .and().httpBasic()
                    .and().logout(logout -> logout
                            .logoutUrl(LOGOUT_URL)
                            .addLogoutHandler((request, response, auth) -> {
                                for (Cookie cookie : request.getCookies()) {
                                    String cookieName = cookie.getName();
                                    if(cookieName.equals(AppMessages.JWT_TOKEN_NAME)) {
                                        Cookie cookieToDelete = new Cookie(cookieName, null);
                                        cookieToDelete.setMaxAge(0);
                                        response.addCookie(cookieToDelete);
                                    }
                                }
                            })
                          );
        http.headers().frameOptions().disable();
    }

    @Bean
    public AuthenticationSuccessHandler appAuthenticationSuccessHandler() {
        return new ApplicationAuthenticationSuccessHandler();
    }
}
