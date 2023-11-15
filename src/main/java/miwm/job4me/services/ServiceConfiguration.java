package miwm.job4me.services;

import miwm.job4me.web.mappers.users.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    UserMapper userMapper() {
        return new UserMapper();
    }

}
