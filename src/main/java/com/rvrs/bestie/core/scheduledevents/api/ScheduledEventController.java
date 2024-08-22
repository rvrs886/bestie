package com.rvrs.bestie.core.scheduledevents.api;

import com.rvrs.bestie.core.scheduledevents.api.dto.ScheduledEventDto;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventTask;
import com.rvrs.bestie.core.scheduledevents.repo.ScheduledEventTaskRepository;
import com.rvrs.bestie.core.scheduledevents.service.ScheduledEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/scheduled-events")
public class ScheduledEventController {

	protected final ScheduledEventTaskRepository scheduledEventTaskRepository;
	protected final ScheduledEventService scheduledEventService;

	public ScheduledEventController(ScheduledEventTaskRepository scheduledEventTaskRepository,
	                                ScheduledEventService scheduledEventService) {
		this.scheduledEventTaskRepository = scheduledEventTaskRepository;
		this.scheduledEventService = scheduledEventService;
	}

	@GetMapping
	public Page<ScheduledEventTask> getScheduledEvents(Pageable pageable) {
		return scheduledEventTaskRepository.findAll(pageable);
	}

	@GetMapping("/{scheduledEventId}")
	public ScheduledEvent getScheduledEvent(@PathVariable UUID scheduledEventId) {
		return scheduledEventService.getScheduledEventById(scheduledEventId);
	}

	@PostMapping
	public void generateScheduledEvent(@RequestBody ScheduledEventDto scheduledEventDto) {
		scheduledEventService.createScheduledEvent(scheduledEventDto);
	}

}
