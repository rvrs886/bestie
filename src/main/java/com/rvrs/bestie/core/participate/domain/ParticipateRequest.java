package com.rvrs.bestie.core.participate.domain;

import com.rvrs.bestie.core.audit.domain.Auditable;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.security.domain.Customer;
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
	private Customer customer;

	@NotAudited
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ScheduledEvent scheduledEvent;

	public ParticipateRequest() {}

	public ParticipateRequest(String message, Customer customer, ScheduledEvent scheduledEvent) {
		this.message = message;
		this.customer = customer;
		this.scheduledEvent = scheduledEvent;
	}

	public void accept() {
		requestStatus = ACCEPTED;
	}

	public void decline() {
		requestStatus = DECLINED;
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

	public Customer getCustomer() {
		return customer;
	}

	public ScheduledEvent getScheduledEvent() {
		return scheduledEvent;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ParticipateRequest that = (ParticipateRequest) o;
		return Objects.equals(id, that.id) && Objects.equals(message, that.message) &&
				requestStatus == that.requestStatus &&
				Objects.equals(customer, that.customer) &&
				Objects.equals(scheduledEvent, that.scheduledEvent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, message, requestStatus, customer, scheduledEvent);
	}
}
