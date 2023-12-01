package miwm.job4me.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static miwm.job4me.messages.AppMessages.*;

@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(FRONT_BRANCH_HOST_AZURE, FRONT_HOST_AZURE, FRONT_HOST, LINKEDIN_HOST, STRIPE_HOST)
                .allowedMethods(CORS_ALLOW_ANY)
                .allowedHeaders("Content-Type", "Accept", "X-Requested-With", "Authorization", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
                .allowCredentials(CORS_ALLOW_CREDENTIALS)
                .maxAge(CORS_MAX_AGE);
    }
}
