package com.rvrs.bestie.core.scheduledevents.service;

import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import com.rvrs.bestie.core.participate.dto.ParticipateRequestDto;
import com.rvrs.bestie.core.participate.repo.ParticipateRequestsRepository;
import com.rvrs.bestie.core.scheduledevents.api.dto.ScheduledEventData;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.core.scheduledevents.repo.ScheduledEventRepository;
import com.rvrs.bestie.security.domain.Customer;
import com.rvrs.bestie.security.util.SecurityUtils;
import com.rvrs.bestie.util.Clock;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class ScheduledEventService {

	private static final Logger log = LoggerFactory.getLogger(ScheduledEventService.class);
	private final ScheduledEventRepository scheduledEventRepository;
	private final ParticipateRequestsRepository participateRequestsRepository;
	private final Clock clock;

	public ScheduledEventService(ScheduledEventRepository scheduledEventRepository,
	                             ParticipateRequestsRepository participateRequestsRepository,
	                             Clock clock) {
		this.scheduledEventRepository = scheduledEventRepository;
		this.participateRequestsRepository = participateRequestsRepository;
		this.clock = clock;
	}

	public ScheduledEvent getScheduledEventById(UUID scheduledEventId) {
		return scheduledEventRepository.findById(scheduledEventId)
				.orElseThrow(() -> {
					log.warn("Could not find scheduled event with id {}", scheduledEventId);
					return new EntityNotFoundException("Scheduled event with id " + scheduledEventId + " not found");
				});
	}

	public void createScheduledEvent(ScheduledEventData scheduledEventData) {
		ScheduledEvent scheduledEvent = new ScheduledEvent(scheduledEventData, clock);
		scheduledEventRepository.save(scheduledEvent);
	}

	@Transactional
	public void addParticipateRequestForScheduledEvent(UUID scheduledEventId, ParticipateRequestDto participateRequestDto) {
		ScheduledEvent scheduledEvent = getScheduledEventById(scheduledEventId);

		ParticipateRequest participateRequest = new ParticipateRequest(
				participateRequestDto.message(),
				(Customer) SecurityUtils.getCurrentUser(),
				scheduledEvent
		);

		participateRequestsRepository.save(participateRequest);
	}
}
