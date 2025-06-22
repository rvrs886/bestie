package com.rvrs.bestie.core.scheduledevents.domain;

import com.rvrs.bestie.core.audit.domain.Auditable;
import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import com.rvrs.bestie.security.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.*;

@Entity
@Table(name = "scheduled_events")
@Audited
public class ScheduledEvent implements Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Embedded
	private ScheduledEventData scheduledEventData;

	@NotAudited
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "scheduledEvent", cascade = CascadeType.REMOVE)
	private List<ParticipateRequest> participateRequests = new ArrayList<>();

	@NotAudited
	@NotEmpty
	@Size(max = 3)
	@ManyToMany(fetch = FetchType.LAZY)
	private List<User> owners;

	public ScheduledEvent() {
	}

	public ScheduledEvent(ScheduledEventData scheduledEventData, User... owners) {
		this.scheduledEventData = scheduledEventData;
		this.owners = List.of(owners);
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

	public List<User> getOwners() {
		return owners;
	}

	public void updateScheduledData(ScheduledEventData scheduledEventData) {
		this.scheduledEventData = scheduledEventData;
	}

	public void setOwners(List<User> owners) {
		this.owners = owners;
	}

	public boolean isOwner(User user) {
		return this.owners.stream().map(User::getId).toList().contains(user.getId());
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
