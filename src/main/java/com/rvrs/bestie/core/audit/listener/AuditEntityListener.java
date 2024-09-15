package com.rvrs.bestie.core.audit.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvrs.bestie.core.audit.domain.AuditEntry;
import com.rvrs.bestie.core.audit.domain.OperationType;
import com.rvrs.bestie.core.audit.repo.AuditEntryRepository;
import com.rvrs.bestie.security.domain.User;
import com.rvrs.bestie.security.util.SecurityUtils;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

@Component
public class AuditEntityListener {

	private final ObjectMapper objectMapper;
	private final AuditEntryRepository auditEntryRepository;

	public AuditEntityListener(ObjectMapper objectMapper, AuditEntryRepository auditEntryRepository) {
		this.objectMapper = objectMapper;
		this.auditEntryRepository = auditEntryRepository;
	}

	@PreUpdate
	public void preUpdate(Object entity) throws JsonProcessingException {
		User user = SecurityUtils.getCurrentUser();
		saveAuditEntry(entity, OperationType.UPDATE, user);
	}

	@PrePersist
	public void prePersist(Object entity) throws JsonProcessingException {
		User user = SecurityUtils.getCurrentUser();
		saveAuditEntry(entity, OperationType.CREATE, user);
	}

	@PreRemove
	public void preRemove(Object entity) throws JsonProcessingException {
		User user = SecurityUtils.getCurrentUser();
		saveAuditEntry(entity, OperationType.DELETE, user);
	}

	private void saveAuditEntry(Object entity, OperationType operationType, User user) throws JsonProcessingException {
		AuditEntry auditEntry = new AuditEntry(
				user,
				operationType,
				entity.getClass().getName(),
				getEntityId(entity),
				entity,
				objectMapper
		);
		auditEntryRepository.save(auditEntry);
	}

	private String getEntityId(Object entity) {
		try {
			return entity.getClass().getMethod("getId").invoke(entity).toString();
		} catch (Exception e) {
			throw new RuntimeException("Failed to get entity ID", e);
		}
	}
}
