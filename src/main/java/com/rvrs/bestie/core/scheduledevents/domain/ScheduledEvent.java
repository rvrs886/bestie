package com.rvrs.bestie.core.scheduledevents.domain;

import com.rvrs.bestie.core.audit.domain.AuditEntry;
import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import com.rvrs.bestie.util.Clock;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Table(name = "scheduled_events")
@Entity
public class ScheduledEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Embedded
	private ScheduledEventData scheduledEventData;

	@Column(nullable = false)
	private LocalDateTime creationDateTime;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "scheduledEvent", cascade = CascadeType.REMOVE)
	private List<ParticipateRequest> participateRequests = new ArrayList<>();

	@OneToMany
	@JoinColumn(name = "audit_entry_id", referencedColumnName = "entity_id")
	private List<AuditEntry> auditEntries = new ArrayList<>();

	public ScheduledEvent() {
	}

	public ScheduledEvent(ScheduledEventData scheduledEventData, Clock clock) {
		this.scheduledEventData = scheduledEventData;
		this.creationDateTime = clock.now();
	}

	public ScheduledEvent(ScheduledEventData scheduledEventData, LocalDateTime creationDateTime) {
		this.scheduledEventData = scheduledEventData;
		this.creationDateTime = creationDateTime;
	}

	public UUID getId() {
		return id;
	}

	public ScheduledEventData getScheduledEventData() {
		return scheduledEventData;
	}

	public List<ParticipateRequest> getParticipateRequests() {
		return participateRequests;
	}

	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	public void addAuditEntry(AuditEntry auditEntry) {
		this.auditEntries.add(auditEntry);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ScheduledEvent that = (ScheduledEvent) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(scheduledEventData, that.scheduledEventData) &&
				Objects.equals(creationDateTime, that.creationDateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, scheduledEventData, creationDateTime);
	}
}
