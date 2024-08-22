package com.rvrs.bestie.core.scheduledevents.repo;

import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduledEventRepository extends JpaRepository<ScheduledEvent, UUID> {
}
