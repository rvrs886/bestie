package com.rvrs.bestie.core.scheduledevents.service;

import com.rvrs.bestie.core.scheduledevents.api.dto.ScheduledEventData;
import com.rvrs.bestie.core.scheduledevents.domain.Location;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventMetadata;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventType;
import com.rvrs.bestie.core.scheduledevents.repo.ScheduledEventRepository;
import com.rvrs.bestie.util.Clock;

import java.time.LocalDateTime;

public class ScheduledEventBuilder {

	private String title;
	private String description;
	private Location location;
	private LocalDateTime scheduledDateTime;
	private ScheduledEventType type;
	private Integer participantsCount;
	private Integer maximumAge;
	private Integer minimumAge;
	private LocalDateTime creationDateTime;

	public static ScheduledEventBuilder scheduledEventBuilder() {
		return new ScheduledEventBuilder();
	}

	public ScheduledEventBuilder withTitle(String title) {
		this.title = title;
		return this;
	}

	public ScheduledEventBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	public ScheduledEventBuilder withLocation(Location location) {
		this.location = location;
		return this;
	}

	public ScheduledEventBuilder withScheduledDateTime(LocalDateTime scheduledDateTime) {
		this.scheduledDateTime = scheduledDateTime;
		return this;
	}

	public ScheduledEventBuilder withType(ScheduledEventType type) {
		this.type = type;
		return this;
	}

	public ScheduledEventBuilder withParticipantsCount(Integer participantsCount) {
		this.participantsCount = participantsCount;
		return this;
	}

	public ScheduledEventBuilder withMaximumAge(Integer maximumAge) {
		this.maximumAge = maximumAge;
		return this;
	}

	public ScheduledEventBuilder withMinimumAge(Integer minimumAge) {
		this.minimumAge = minimumAge;
		return this;
	}

	public ScheduledEventBuilder withCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
		return this;
	}

	public ScheduledEvent build() {
		ScheduledEventMetadata scheduledEventMetadata = new ScheduledEventMetadata(
				participantsCount,
				minimumAge,
				maximumAge
		);
		ScheduledEventData scheduledEventData = new ScheduledEventData(
				title,
				description,
				location,
				scheduledDateTime,
				type,
				scheduledEventMetadata
		);
		return new ScheduledEvent(
				scheduledEventData,
				creationDateTime
		);
	}

	public ScheduledEvent build(ScheduledEventRepository scheduledEventRepository) {
		ScheduledEventMetadata scheduledEventMetadata = new ScheduledEventMetadata(
				participantsCount,
				minimumAge,
				maximumAge
		);
		ScheduledEventData scheduledEventData = new ScheduledEventData(
				title,
				description,
				location,
				scheduledDateTime,
				type,
				scheduledEventMetadata
		);
		return scheduledEventRepository.save(
				new ScheduledEvent(
						scheduledEventData,
						creationDateTime
				)
		);
	}
}
