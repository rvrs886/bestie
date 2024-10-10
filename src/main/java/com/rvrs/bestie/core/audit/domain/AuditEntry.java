package com.rvrs.bestie.core.audit.domain;

import com.rvrs.bestie.core.audit.listener.AuditListener;
import jakarta.persistence.*;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@RevisionEntity(AuditListener.class)
@Table(name = "audit_log")
public class AuditEntry {

	@Id
	@GeneratedValue
	@RevisionNumber
	private Long revisionNumber;

	@RevisionTimestamp
	private LocalDateTime revisionTimestamp;

	private String author;

	public AuditEntry() {
	}

	public AuditEntry(String author) {
		this.author = author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public LocalDateTime getRevisionTimestamp() {
		return revisionTimestamp;
	}

	public String getAuthor() {
		return author;
	}

	public Long getRevisionNumber() {
		return revisionNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AuditEntry that = (AuditEntry) o;
		return Objects.equals(revisionNumber, that.revisionNumber) &&
				Objects.equals(revisionTimestamp, that.revisionTimestamp) &&
				Objects.equals(author, that.author);
	}

	@Override
	public int hashCode() {
		return Objects.hash(revisionNumber, revisionTimestamp, author);
	}
}
