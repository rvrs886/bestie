package com.rvrs.bestie;

import com.rvrs.bestie.util.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class BestieConfig {

	@Bean
	Clock clock() {
		return LocalDateTime::now;
	}
}
