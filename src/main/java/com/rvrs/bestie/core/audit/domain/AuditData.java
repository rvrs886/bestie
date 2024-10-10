package com.rvrs.bestie.core.audit.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class AuditData<T> {

	private final OperationType operationType;

	private final LocalDateTime revisionTimestamp;

	private final Long revisionNumber;

	private final T entity;

	public AuditData(OperationType operationType, LocalDateTime revisionTimestamp, Long revisionNumber, Object entity) {
		this.operationType = operationType;
		this.revisionTimestamp = revisionTimestamp;
		this.revisionNumber = revisionNumber;
		this.entity = (T) entity;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public LocalDateTime getRevisionTimestamp() {
		return revisionTimestamp;
	}

	public Long getRevisionNumber() {
		return revisionNumber;
	}

	public T getEntity() {
		return entity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AuditData<?> auditData = (AuditData<?>) o;
		return operationType == auditData.operationType && Objects.equals(revisionTimestamp, auditData.revisionTimestamp) && Objects.equals(revisionNumber, auditData.revisionNumber) && Objects.equals(entity, auditData.entity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(operationType, revisionTimestamp, revisionNumber, entity);
	}
}
