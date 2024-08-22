package com.rvrs.bestie.core.scheduledevents.repo;

import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ScheduledEventRepository extends JpaRepository<ScheduledEvent, UUID> {

	@Query("SELECT se.participateRequests FROM ScheduledEvent se WHERE se.id = :id")
	Page<ParticipateRequest> getParticipateRequestsByScheduledEventId(@Param("id") UUID id, Pageable pageable);

}
