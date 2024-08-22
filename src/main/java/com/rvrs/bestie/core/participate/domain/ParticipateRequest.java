package com.rvrs.bestie.core.participate.domain;

import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.security.domain.Customer;
import jakarta.persistence.*;

import java.util.UUID;

import static com.rvrs.bestie.core.participate.domain.RequestStatus.PENDING;

@Entity
@Table(name = "participate_requests")
public class ParticipateRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String message;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RequestStatus requestStatus = PENDING;

	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	private ScheduledEvent scheduledEvent;
}
