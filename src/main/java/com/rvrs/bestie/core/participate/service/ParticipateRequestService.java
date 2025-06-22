package com.rvrs.bestie.core.participate.service;

import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import com.rvrs.bestie.core.participate.domain.RequestStatus;
import com.rvrs.bestie.core.participate.repo.ParticipateRequestsRepository;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.security.domain.User;
import com.rvrs.bestie.security.util.SecurityContextUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ParticipateRequestService {

	private final ParticipateRequestsRepository participateRequestsRepository;

	public ParticipateRequestService(ParticipateRequestsRepository participateRequestsRepository) {
		this.participateRequestsRepository = participateRequestsRepository;
	}

	public void updateParticipateRequestStatus(Long participateRequestId, RequestStatus requestStatus) {
		ParticipateRequest participateRequest = findParticipateRequestById(participateRequestId);
		ScheduledEvent scheduledEvent = participateRequest.getScheduledEvent();
		User currentUser = SecurityContextUtils.getCurrentAuthentication().getUser();

		if (scheduledEvent.isOwner(currentUser)) {
			participateRequest.setRequestStatus(requestStatus);
		} else {
			throw new IllegalArgumentException("Scheduled event with id " + scheduledEvent.getId() + " does not belong to user " + currentUser.getUsername());
		}
	}

	public ParticipateRequest findParticipateRequestById(Long id) {
		return participateRequestsRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Participate request with id " + id + " not found"));
	}
}
