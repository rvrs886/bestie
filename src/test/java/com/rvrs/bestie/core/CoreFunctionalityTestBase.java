package com.rvrs.bestie.core;

import com.rvrs.bestie.IntegrationTestBase;
import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import com.rvrs.bestie.core.scheduledevents.domain.Location;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventType;
import com.rvrs.bestie.security.util.SecurityContextUtils;

import java.time.LocalDateTime;

import static com.rvrs.bestie.core.scheduledevents.service.ScheduledEventBuilder.scheduledEventBuilder;

public class CoreFunctionalityTestBase extends IntegrationTestBase {

	protected ScheduledEvent existingScheduledEvent() {
		return scheduledEventBuilder()
				.withTitle("test title")
				.withType(ScheduledEventType.ENTERTAINMENT)
				.withDescription("description")
				.withScheduledDateTime(LocalDateTime.of(2024, 10, 20, 12, 30, 20))
				.withLocation(testLocation())
				.withMaximumAge(50)
				.withMinimumAge(18)
				.withUser(SecurityContextUtils.getCurrentAuthentication().getUser())
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
						SecurityContextUtils.getCurrentAuthentication().getUser(),
						scheduledEvent
				)
		);
	}
}
