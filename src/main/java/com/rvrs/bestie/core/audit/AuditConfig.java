package com.rvrs.bestie.core.audit;

import com.rvrs.bestie.core.audit.service.AuditService;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.rvrs.bestie")
public class AuditConfig {

	@Bean
	AuditService auditService(EntityManager entityManager) {
		return new AuditService(entityManager);
	}
}
