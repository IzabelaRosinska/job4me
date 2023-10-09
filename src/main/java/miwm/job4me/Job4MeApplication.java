package miwm.job4me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity()
public class Job4MeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Job4MeApplication.class, args);
	}

}
