package com.rvrs.bestie.core.scheduledevents.service;

import com.rvrs.bestie.IntegrationTestBase;
import com.rvrs.bestie.core.scheduledevents.domain.Location;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;

import static com.rvrs.bestie.core.scheduledevents.service.ScheduledEventBuilder.scheduledEventBuilder;
import static java.time.LocalDateTime.now;

public class ScheduledEventTestBase extends IntegrationTestBase {

	protected ScheduledEvent existingScheduledEvent() {
		return scheduledEventBuilder()
				.withTitle("test title")
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
				"Poland",
				"xxx"
		);
	}

}
