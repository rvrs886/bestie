package com.rvrs.bestie.core.scheduledevents.api;

import com.rvrs.bestie.core.audit.domain.AuditData;
import com.rvrs.bestie.core.audit.service.AuditService;
import com.rvrs.bestie.core.participate.dto.ParticipateRequestDto;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventData;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.core.scheduledevents.repo.ScheduledEventRepository;
import com.rvrs.bestie.core.scheduledevents.service.ScheduledEventService;
import com.rvrs.bestie.security.domain.User;
import com.rvrs.bestie.security.repo.UserRepository;
import com.rvrs.bestie.security.service.UserService;
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
	private final AuditService auditService;

	@PersistenceContext
	protected final EntityManager entityManager;
	private final UserRepository userRepository;

	public ScheduledEventController(ScheduledEventRepository scheduledEventRepository,
	                                ScheduledEventService scheduledEventService,
	                                AuditService auditService,
	                                EntityManager entityManager,
	                                UserRepository userRepository) {
		this.scheduledEventRepository = scheduledEventRepository;
		this.scheduledEventService = scheduledEventService;
		this.auditService = auditService;
		this.entityManager = entityManager;
		this.userRepository = userRepository;
	}

	@GetMapping
	public Page<ScheduledEvent> getScheduledEvents(Pageable pageable) {
		return scheduledEventRepository.findAll(pageable);
	}

	@GetMapping("/{id}")
	public ScheduledEvent getScheduledEvent(@PathVariable UUID id) {
		return scheduledEventService.getScheduledEventById(id);
	}

	@PutMapping("/{id}/owners")
	public void updateOwners(@PathVariable UUID id, @RequestBody List<UUID> ownerCandidates) {
		ScheduledEvent scheduledEvent = scheduledEventService.getScheduledEventById(id);
		List<User> owners = userRepository.findAllById(ownerCandidates);
		scheduledEvent.setOwners(owners);
		scheduledEventRepository.save(scheduledEvent);
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
		return auditService.getAuditLogFor(ScheduledEvent.class, id);
	}

	@PostMapping("/{id}/request")
	public void addParticipateRequest(@PathVariable UUID id, @Valid @RequestBody ParticipateRequestDto participateRequestDto) {
		scheduledEventService.addParticipateRequestForScheduledEvent(id, participateRequestDto);
	}

}
