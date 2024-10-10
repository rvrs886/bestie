package com.rvrs.bestie.core.audit.listener;

import com.rvrs.bestie.core.audit.domain.AuditEntry;
import com.rvrs.bestie.security.util.SecurityUtils;
import org.hibernate.envers.RevisionListener;

public class AuditListener implements RevisionListener {

	@Override
	public void newRevision(Object o) {
		AuditEntry auditEntry = (AuditEntry) o;
		auditEntry.setAuthor(SecurityUtils.getCurrentUser().getUsername());
	}
}
