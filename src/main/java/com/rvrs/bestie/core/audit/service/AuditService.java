package com.rvrs.bestie.core.audit.service;

import com.rvrs.bestie.core.audit.domain.AuditData;
import com.rvrs.bestie.core.audit.domain.AuditEntry;
import com.rvrs.bestie.core.audit.domain.Auditable;
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rvrs.bestie.core.audit.domain.OperationType.getOperationTypeFor;

@Service
public class AuditService {

	private final EntityManager em;

	public AuditService(EntityManager em) {
		this.em = em;
	}

	public <T extends Auditable> List<AuditData<T>> getAuditLogFor(Class<T> clazz, Object id) {
		AuditReader auditReader = auditReader();

		List<?> auditData;

		try {
			auditData = auditReader.createQuery()
					.forRevisionsOfEntity(clazz, false, false)
					.add(AuditEntity.id().eq(id))
					.getResultList();
		} finally {
			em.close();
		}

		return auditData.stream()
				.map(entry -> {
					Object[] array = (Object[]) entry;
					return prepareAuditData(clazz, array);
				}).toList();
	}

	@SuppressWarnings("unchecked")
	private <T extends Auditable> AuditData<T> prepareAuditData(Class<T> clazz, Object[] dataArray) {
		T entity = (T) dataArray[0];
		AuditEntry auditEntry = (AuditEntry) dataArray[1];
		RevisionType revisionType = (RevisionType) dataArray[2];

		return new AuditData<>(
				getOperationTypeFor(revisionType),
				auditEntry.getRevisionTimestamp(),
				entity
		);
	}

	private AuditReader auditReader() {
		return AuditReaderFactory.get(em);
	}
}
