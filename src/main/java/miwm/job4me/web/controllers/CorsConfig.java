package miwm.job4me.web.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class CorsConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://mango-moss-0c13e2b03-32.westeurope.3.azurestaticapps.net", "http://localhost:4200", "https://www.linkedin.com")
                .allowedMethods("POST, GET, PATCH, OPTIONS, DELETE, PUT")
                .allowedHeaders("Origin, Content-Type, Authorization, Accept, X-Requested-With, remember-me")
                .allowCredentials(true)
                .maxAge(3600); // You can set the max age of the preflight request in seconds
    }
}
