package miwm.job4me.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.web.client.RestTemplate;

import static miwm.job4me.messages.AppMessages.*;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Value(LINKEDIN_ID_PARAM)
    private String linkedinClientId;

    @Value(LINKEDIN_SECRET_PARAM)
    private String linkedinClientSecret;

    @Bean
    public LinkedInConnectionFactory linkedInConnectionFactory() {
        return new LinkedInConnectionFactory(linkedinClientId, linkedinClientSecret);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
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
                return TEST_USER;
            }
        };
    }
}
