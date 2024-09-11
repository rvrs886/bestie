package com.rvrs.bestie.core;

import com.rvrs.bestie.IntegrationTestBase;
import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import com.rvrs.bestie.core.scheduledevents.domain.Location;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventType;
import com.rvrs.bestie.security.domain.Customer;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.rvrs.bestie.core.scheduledevents.service.ScheduledEventBuilder.scheduledEventBuilder;
import static java.time.LocalDateTime.now;

public class CoreFunctionalityTestBase extends IntegrationTestBase {

	protected ScheduledEvent existingScheduledEvent() {
		return scheduledEventBuilder()
				.withTitle("test title")
				.withType(ScheduledEventType.ENTERTAINMENT)
				.withDescription("description")
				.withScheduledDateTime(now())
				.withLocation(testLocation())
				.withMaximumAge(50)
				.withMinimumAge(18)
				.withCreationDateTime(now())
				.build(scheduledEventRepository);
	}

	protected Location testLocation() {
		return new Location(
				"54-234",
				"Wroclaw",
				"Poland",
				"xxx",
				"gym"
		);
	}

	protected ParticipateRequest prepareParticipateRequest(ScheduledEvent scheduledEvent) {
		return participateRequestsRepository.save(
				new ParticipateRequest(
						"test message",
						(Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
						scheduledEvent
				)
		);
	}
}
