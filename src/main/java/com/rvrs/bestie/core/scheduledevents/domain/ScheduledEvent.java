package com.rvrs.bestie.core.scheduledevents.domain;

import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import com.rvrs.bestie.core.scheduledevents.api.dto.ScheduledEventData;
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

	private LocalDateTime creationDateTime;

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<ParticipateRequest> participateRequests = new ArrayList<>();

	private ScheduledEvent() {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ScheduledEvent that = (ScheduledEvent) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(scheduledEventData, that.scheduledEventData) &&
				Objects.equals(creationDateTime, that.creationDateTime) &&
				Objects.equals(participateRequests, that.participateRequests);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, scheduledEventData, creationDateTime, participateRequests);
	}
}
