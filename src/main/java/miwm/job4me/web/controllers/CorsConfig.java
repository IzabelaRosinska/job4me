package miwm.job4me.web.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://mango-moss-0c13e2b03-32.westeurope.3.azurestaticapps.net", "https://www.linkedin.com") // Replace with your front-end domain
                .allowedMethods("GET", "POST")
                .allowedHeaders("Origin", "Content-Type", "Accept");
    }
}
