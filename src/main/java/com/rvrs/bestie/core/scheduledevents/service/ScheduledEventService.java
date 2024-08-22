package com.rvrs.bestie.core.scheduledevents.service;

import com.rvrs.bestie.core.scheduledevents.api.dto.ScheduledEventDto;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventTask;
import com.rvrs.bestie.core.scheduledevents.repo.ScheduledEventRepository;
import com.rvrs.bestie.core.scheduledevents.repo.ScheduledEventTaskRepository;
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
	private final ScheduledEventTaskRepository scheduledEventTaskRepository;
	private final ExecutorService executorService;
	private final Clock clock;

	public ScheduledEventService(ScheduledEventRepository scheduledEventRepository,
	                             ScheduledEventTaskRepository scheduledEventTaskRepository,
	                             ExecutorService scheduledEventsExecutorService,
	                             Clock clock) {
		this.scheduledEventRepository = scheduledEventRepository;
		this.scheduledEventTaskRepository = scheduledEventTaskRepository;
		this.executorService = scheduledEventsExecutorService;
		this.clock = clock;
	}

	public ScheduledEvent getScheduledEventById(UUID scheduledEventId) {
		return scheduledEventRepository.findById(scheduledEventId)
				.orElseThrow(() -> {
					log.warn("Could not find scheduled event with id {}", scheduledEventId);
					return new EntityNotFoundException("Scheduled event with id " + scheduledEventId + " not found");
				});
	}

	@Transactional
	public void createScheduledEvent(ScheduledEventDto scheduledEventDto) {
		ScheduledEventTask eventTask = new ScheduledEventTask(scheduledEventDto, clock);
		scheduledEventTaskRepository.save(eventTask);

		executorService.submit(() -> {
			try {
				ScheduledEvent scheduledEvent = new ScheduledEvent(scheduledEventDto);
				scheduledEventRepository.save(scheduledEvent);
				eventTask.finish(scheduledEvent, null, clock);
			} catch (Exception ex) {
				log.error("An exception occurred during scheduled event generation", ex);
				eventTask.finish(null, ex.getMessage(), clock);
			}
			scheduledEventTaskRepository.save(eventTask);
		});
	}
}
