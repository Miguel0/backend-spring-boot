package main.com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Main application definition class.
 * 
 * @author Miguel Isasmendi
 *
 */
@SpringBootApplication
@EnableWebMvc
@EnableScheduling
@ComponentScan(basePackages = { "main.com.resource.impl", "main.com.service.impl", "main.com.dao.impl" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}