package com.rvrs.bestie.core.participate.domain;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.rvrs.bestie.core.audit.domain.Auditable;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.security.domain.User;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.Objects;

import static com.rvrs.bestie.core.participate.domain.RequestStatus.*;

@Entity
@Table(name = "participate_requests")
@Audited
public class ParticipateRequest implements Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String message;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RequestStatus requestStatus = PENDING;

	@NotAudited
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	@JsonIncludeProperties("id")
	@NotAudited
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ScheduledEvent scheduledEvent;

	public ParticipateRequest() {}

	public ParticipateRequest(String message, User user, ScheduledEvent scheduledEvent) {
		this.message = message;
		this.user = user;
		this.scheduledEvent = scheduledEvent;
	}

	public Long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public User getUser() {
		return user;
	}

	public ScheduledEvent getScheduledEvent() {
		return scheduledEvent;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ParticipateRequest that = (ParticipateRequest) o;
		return Objects.equals(id, that.id) && Objects.equals(message, that.message) &&
				requestStatus == that.requestStatus &&
				Objects.equals(user, that.user) &&
				Objects.equals(scheduledEvent, that.scheduledEvent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, message, requestStatus, user, scheduledEvent);
	}
}
