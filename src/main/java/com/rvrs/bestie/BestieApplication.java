package com.rvrs.bestie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class BestieApplication {

	public static void main(String[] args) {
		SpringApplication.run(BestieApplication.class, args);
	}

}
