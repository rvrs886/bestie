package com.rvrs.bestie.core.scheduledevents.api;

import com.rvrs.bestie.core.participate.dto.ParticipateRequestDto;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventData;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.core.scheduledevents.repo.ScheduledEventRepository;
import com.rvrs.bestie.core.scheduledevents.service.ScheduledEventService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/scheduled-events")
public class ScheduledEventController {

	protected final ScheduledEventRepository scheduledEventRepository;
	protected final ScheduledEventService scheduledEventService;

	public ScheduledEventController(ScheduledEventRepository scheduledEventRepository,
	                                ScheduledEventService scheduledEventService) {
		this.scheduledEventRepository = scheduledEventRepository;
		this.scheduledEventService = scheduledEventService;
	}

	@GetMapping
	public Page<ScheduledEvent> getScheduledEvents(Pageable pageable) {
		return scheduledEventRepository.findAll(pageable);
	}

	@GetMapping("/{scheduledEventId}")
	public ScheduledEvent getScheduledEvent(@PathVariable UUID scheduledEventId) {
		return scheduledEventService.getScheduledEventById(scheduledEventId);
	}

	@PostMapping
	public void generateScheduledEvent(@RequestBody ScheduledEventData scheduledEventData) {
		scheduledEventService.createScheduledEvent(scheduledEventData);
	}

	@PostMapping("/{id}/request")
	public void addParticipateRequest(@PathVariable UUID id, @Valid @RequestBody ParticipateRequestDto participateRequestDto) {
		scheduledEventService.addParticipateRequestForScheduledEvent(id, participateRequestDto);
	}

}
