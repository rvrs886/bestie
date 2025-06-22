package com.rvrs.bestie.core.audit.listener;

import com.rvrs.bestie.core.audit.domain.AuditEntry;
import com.rvrs.bestie.security.util.SecurityContextUtils;
import org.hibernate.envers.RevisionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditListener implements RevisionListener {

	private static final Logger log = LoggerFactory.getLogger(AuditListener.class);

	@Override
	public void newRevision(Object o) {
		AuditEntry auditEntry = (AuditEntry) o;
		String author = null;
		try {
			author = SecurityContextUtils.getCurrentAuthentication().getUsername();
		} catch (Exception e) {
			log.warn("Error when getting security context, setting null author value");
		}

		auditEntry.setAuthor(author);
	}
}
