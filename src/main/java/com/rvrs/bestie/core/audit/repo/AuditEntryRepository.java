package com.rvrs.bestie.core.audit.repo;

import com.rvrs.bestie.core.audit.domain.AuditEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditEntryRepository extends JpaRepository<AuditEntry, UUID> {
}
