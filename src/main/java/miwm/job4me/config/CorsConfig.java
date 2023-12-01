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
                .allowedOrigins("https://mango-moss-0c13e2b03-32.westeurope.3.azurestaticapps.net", "https://mango-moss-0c13e2b03.3.azurestaticapps.net", "http://localhost:4200", "https://www.linkedin.com")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
