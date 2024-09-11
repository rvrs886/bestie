package com.rvrs.bestie.core.audit.listener;

import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class ScheduledEventAuditor implements AuditorAware<ScheduledEvent> {

	@Override
	public Optional<ScheduledEvent> getCurrentAuditor() {
		return Optional.empty();
	}
}
