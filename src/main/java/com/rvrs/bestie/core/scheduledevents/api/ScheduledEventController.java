package com.rvrs.bestie.core.scheduledevents.api;

import com.rvrs.bestie.core.audit.domain.AuditData;
import com.rvrs.bestie.core.audit.service.AuditDataParser;
import com.rvrs.bestie.core.participate.dto.ParticipateRequestDto;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventData;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.core.scheduledevents.repo.ScheduledEventRepository;
import com.rvrs.bestie.core.scheduledevents.service.ScheduledEventService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/scheduled-events")
public class ScheduledEventController {

	private final ScheduledEventRepository scheduledEventRepository;
	private final ScheduledEventService scheduledEventService;
	private final AuditDataParser auditDataParser;

	@PersistenceContext
	protected final EntityManager entityManager;

	public ScheduledEventController(ScheduledEventRepository scheduledEventRepository,
	                                ScheduledEventService scheduledEventService,
	                                AuditDataParser auditDataParser,
	                                EntityManager entityManager) {
		this.scheduledEventRepository = scheduledEventRepository;
		this.scheduledEventService = scheduledEventService;
		this.auditDataParser = auditDataParser;
		this.entityManager = entityManager;
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

	@PutMapping("/{id}")
	public void updateScheduledEvent(@PathVariable UUID id, @RequestBody ScheduledEventData scheduledEventData) {
		scheduledEventService.updateScheduledEvent(id, scheduledEventData);
	}

	@GetMapping("/{id}/audit-log")
	public List<AuditData<ScheduledEvent>> getAuditLog(@PathVariable UUID id) {
		return auditDataParser.getAuditLogFor(ScheduledEvent.class, id);
	}

	@PostMapping("/{id}/request")
	public void addParticipateRequest(@PathVariable UUID id, @Valid @RequestBody ParticipateRequestDto participateRequestDto) {
		scheduledEventService.addParticipateRequestForScheduledEvent(id, participateRequestDto);
	}

}
