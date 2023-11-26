package miwm.job4me.web.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Value("${spring.social.linkedin.app-id}")
    private String linkedinClientId;

    @Value("${spring.social.linkedin.app-secret}")
    private String linkedinClientSecret;

    @Bean
    public LinkedInConnectionFactory linkedInConnectionFactory() {
        return new LinkedInConnectionFactory(linkedinClientId, linkedinClientSecret);
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        configurer.addConnectionFactory(new LinkedInConnectionFactory(linkedinClientId, linkedinClientSecret));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new UserIdSource() {
            @Override
            public String getUserId() {
                // Provide the user ID (username) - for demo purposes
                return "testuser";
            }
        };
    }
}
