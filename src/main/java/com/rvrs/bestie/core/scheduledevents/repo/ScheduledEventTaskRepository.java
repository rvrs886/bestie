package com.rvrs.bestie.core.scheduledevents.repo;

import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledEventTaskRepository extends JpaRepository<ScheduledEventTask, Long> {
}
