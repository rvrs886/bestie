package com.rvrs.bestie.core.participate.api;

import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import com.rvrs.bestie.core.participate.repo.ParticipateRequestsRepository;
import com.rvrs.bestie.core.participate.service.ParticipateRequestService;
import com.rvrs.bestie.security.domain.User;
import com.rvrs.bestie.security.util.SecurityContextUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.rvrs.bestie.core.participate.domain.RequestStatus.ACCEPTED;
import static com.rvrs.bestie.core.participate.domain.RequestStatus.DECLINED;

@RestController
@RequestMapping("/participate-requests")
public class ParticipateRequestController {

	private final ParticipateRequestsRepository participateRequestsRepository;
	private final ParticipateRequestService participateRequestService;

	public ParticipateRequestController(ParticipateRequestsRepository participateRequestsRepository,
	                                    ParticipateRequestService participateRequestService) {
		this.participateRequestsRepository = participateRequestsRepository;
		this.participateRequestService = participateRequestService;
	}

	@GetMapping("/self")
	public Page<ParticipateRequest> getParticipateRequestsForCurrentUser(Pageable pageable) {
		User user = SecurityContextUtils.getCurrentAuthentication().getUser();
		return participateRequestsRepository.findParticipateRequestsByUser_Id(user.getId(), pageable);
	}

	@PostMapping("/{id}/accept")
	public void acceptParticipateRequest(@PathVariable("id") Long id) {
		participateRequestService.updateParticipateRequestStatus(id, ACCEPTED);
	}

	@PostMapping("/{id}/decline")
	public void declineParticipateRequest(@PathVariable("id") Long id) {
		participateRequestService.updateParticipateRequestStatus(id, DECLINED);
	}

	@DeleteMapping("/{id}")
	public void deleteParticipateRequest(@PathVariable("id") Long id) {
		participateRequestsRepository.deleteById(id);
	}

}
