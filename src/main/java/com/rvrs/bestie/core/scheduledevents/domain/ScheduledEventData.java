package com.rvrs.bestie.core.scheduledevents.domain;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public record ScheduledEventData(String title,
                                 String description,
                                 Location location,
                                 LocalDateTime scheduledDateTime,
                                 ScheduledEventType scheduledEventType,
                                 ScheduledEventMetadata scheduledEventMetadata) {

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ScheduledEventData that = (ScheduledEventData) o;
		return Objects.equals(title, that.title) &&
				Objects.equals(location, that.location) &&
				Objects.equals(description, that.description) &&
				Objects.equals(scheduledDateTime, that.scheduledDateTime) &&
				scheduledEventType == that.scheduledEventType &&
				Objects.equals(scheduledEventMetadata, that.scheduledEventMetadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, description, location, scheduledDateTime, scheduledEventType, scheduledEventMetadata);
	}
}
