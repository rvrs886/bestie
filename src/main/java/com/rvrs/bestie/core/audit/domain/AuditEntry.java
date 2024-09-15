package com.rvrs.bestie.core.audit.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvrs.bestie.security.domain.User;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Table(name = "audit_entries")
@Entity
public class AuditEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	private User user;

	@Enumerated(EnumType.STRING)
	private OperationType operationType;

	private String entityType;

	private String entityId;

	private String serializedObject;

	public AuditEntry(User user,
	                  OperationType operationType,
	                  String entityType,
	                  String entityId,
	                  Object objectBeforeUpdate,
	                  ObjectMapper objectMapper) throws JsonProcessingException {
		this.user = user;
		this.operationType = operationType;
		this.serializedObject = objectMapper.writeValueAsString(objectBeforeUpdate);
		this.entityType = objectBeforeUpdate.getClass().getName();
	}

	public UUID getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public String getSerializedObject() {
		return serializedObject;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AuditEntry that = (AuditEntry) o;
		return Objects.equals(id, that.id) && Objects.equals(user, that.user) && operationType == that.operationType && Objects.equals(serializedObject, that.serializedObject);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, user, operationType, serializedObject);
	}
}
