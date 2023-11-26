package miwm.job4me.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import miwm.job4me.messages.ExceptionMessages;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static miwm.job4me.messages.AppMessages.LOGIN_URL;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final SecretKey secretKey;

    public JwtTokenVerifier(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String cookieToken = request.getHeader("authorization");
        if(cookieToken != null && !cookieToken.equals("") && !cookieToken.equals("undefined")) {
            try {
                Jws<Claims> claimsJws = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(cookieToken);

                Claims body = claimsJws.getBody();
                String username = body.getSubject();
                var authorities = (List<Map<String, String>>) body.get("authorities");

                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                        .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                        .collect(Collectors.toSet());

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException exception) {
                throw new IllegalStateException(ExceptionMessages.untrustedToken(cookieToken));
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals(LOGIN_URL);
    }
}
