package com.rvrs.bestie.core.participate.repo;

import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParticipateRequestsRepository extends JpaRepository<ParticipateRequest, Long> {

	Page<ParticipateRequest> findParticipateRequestsByCustomer_Id(UUID customerId, Pageable pageable);

}
