package com.rvrs.bestie.core.scheduledevents.domain;

import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import com.rvrs.bestie.core.scheduledevents.api.dto.ScheduledEventDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "scheduled_events")
public class ScheduledEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Embedded
	private Location location;

	@Column(nullable = false)
	private LocalDateTime scheduledDateTime;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ScheduledEventType type;

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Set<ParticipateRequest> participateRequests = new HashSet<>();

	public ScheduledEvent() {
	}

	public ScheduledEvent(ScheduledEventDto scheduledEventDto) {
		this(
				scheduledEventDto.scheduledEventType(),
				scheduledEventDto.scheduledDateTime(),
				scheduledEventDto.location(),
				scheduledEventDto.description(),
				scheduledEventDto.title()
		);
	}

	public ScheduledEvent(ScheduledEventType type,
	                      LocalDateTime scheduledDateTime,
	                      Location location,
	                      String description,
	                      String title) {
		this.type = type;
		this.scheduledDateTime = scheduledDateTime;
		this.location = location;
		this.description = description;
		this.title = title;
	}

	public UUID getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Location getLocation() {
		return location;
	}

	public LocalDateTime getScheduledDateTime() {
		return scheduledDateTime;
	}

	public ScheduledEventType getType() {
		return type;
	}

	public Set<ParticipateRequest> getParticipateRequests() {
		return participateRequests;
	}

	public void addParticipateRequest(ParticipateRequest participateRequest) {
		this.participateRequests.add(participateRequest);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ScheduledEvent that = (ScheduledEvent) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(title, that.title) &&
				Objects.equals(description, that.description) &&
				Objects.equals(location, that.location) &&
				Objects.equals(scheduledDateTime, that.scheduledDateTime) &&
				type == that.type &&
				Objects.equals(participateRequests, that.participateRequests);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, description, location, scheduledDateTime, type, participateRequests);
	}

	@Override
	public String toString() {
		return "ScheduledEvent{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", location=" + location +
				", scheduledDateTime=" + scheduledDateTime +
				", type=" + type +
				'}';
	}
}
