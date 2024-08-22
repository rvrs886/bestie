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

	private final ScheduledEventTaskRepository scheduledEventTaskRepository;
	private final ScheduledEventService scheduledEventService;

	public ScheduledEventController(ScheduledEventTaskRepository scheduledEventTaskRepository,
	                                ScheduledEventService scheduledEventService) {
		this.scheduledEventTaskRepository = scheduledEventTaskRepository;
		this.scheduledEventService = scheduledEventService;
	}

	@GetMapping
	public Page<ScheduledEventTask> getScheduledEvents(Pageable pageable) {
		return scheduledEventTaskRepository.findAll(pageable);
	}

	@GetMapping("/{id}")
	public ScheduledEvent getScheduledEvent(@PathVariable UUID id) {
		return scheduledEventService.getScheduledEventById(id);
	}

	@PostMapping
	public void generateScheduledEvent(@RequestBody ScheduledEventDto scheduledEventDto) {
		scheduledEventService.createScheduledEvent(scheduledEventDto);
	}

}
