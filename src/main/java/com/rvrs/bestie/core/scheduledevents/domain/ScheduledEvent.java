package com.rvrs.bestie.core.scheduledevents.domain;

import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.*;

@Entity
@Table(name = "scheduled_events")
@Audited
public class ScheduledEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Embedded
	private ScheduledEventData scheduledEventData;

	@NotAudited
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "scheduledEvent", cascade = CascadeType.REMOVE)
	private List<ParticipateRequest> participateRequests = new ArrayList<>();

	public ScheduledEvent() {
	}

	public ScheduledEvent(ScheduledEventData scheduledEventData) {
		this.scheduledEventData = scheduledEventData;
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

	public void updateScheduledData(ScheduledEventData scheduledEventData) {
		this.scheduledEventData = scheduledEventData;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ScheduledEvent that = (ScheduledEvent) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(scheduledEventData, that.scheduledEventData) &&
				Objects.equals(participateRequests, that.participateRequests);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, scheduledEventData, participateRequests);
	}
}
