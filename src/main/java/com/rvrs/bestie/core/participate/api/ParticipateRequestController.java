package com.rvrs.bestie.core.participate.api;

import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import com.rvrs.bestie.core.participate.repo.ParticipateRequestsRepository;
import com.rvrs.bestie.security.domain.User;
import com.rvrs.bestie.security.util.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participate-requests")
public class ParticipateRequestController {

	private final ParticipateRequestsRepository participateRequestsRepository;

	public ParticipateRequestController(ParticipateRequestsRepository participateRequestsRepository) {
		this.participateRequestsRepository = participateRequestsRepository;
	}

	@GetMapping("/self")
	public Page<ParticipateRequest> getParticipateRequestsForCurrentUser(Pageable pageable) {
		User user = SecurityUtils.getCurrentUser().getUser();
		return participateRequestsRepository.findParticipateRequestsByCustomer_Id(user.getId(), pageable);
	}

	@PostMapping("/{id}/accept")
	public void acceptParticipateRequest(@PathVariable("id") Long id) {
		ParticipateRequest participateRequest = participateRequestsRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Participate request with id " + id + " not found"));

		participateRequest.accept();
		participateRequestsRepository.save(participateRequest);
	}

	@PostMapping("/{id}/decline")
	public void declineParticipateRequest(@PathVariable("id") Long id) {
		ParticipateRequest participateRequest = participateRequestsRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Participate request with id " + id + " not found"));

		participateRequest.decline();
		participateRequestsRepository.save(participateRequest);
	}

	@DeleteMapping("/{id}")
	public void deleteParticipateRequest(@PathVariable("id") Long id) {
		participateRequestsRepository.deleteById(id);
	}

}
