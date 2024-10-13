package com.rvrs.bestie.core.audit.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public record AuditData<T>(OperationType operationType, LocalDateTime revisionTimestamp, T entity) {

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AuditData<?> auditData = (AuditData<?>) o;
		return Objects.equals(entity, auditData.entity) &&
				operationType == auditData.operationType &&
				Objects.equals(revisionTimestamp, auditData.revisionTimestamp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(operationType, revisionTimestamp, entity);
	}
}
