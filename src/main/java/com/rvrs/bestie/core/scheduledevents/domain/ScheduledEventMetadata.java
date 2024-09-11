package com.rvrs.bestie.core.scheduledevents.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record ScheduledEventMetadata(Integer participantsCount, Integer minimumAge, Integer maximumAge) {

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ScheduledEventMetadata that = (ScheduledEventMetadata) o;
		return Objects.equals(participantsCount, that.participantsCount) &&
				Objects.equals(minimumAge, that.minimumAge) &&
				Objects.equals(maximumAge, that.maximumAge);
	}

	@Override
	public int hashCode() {
		return Objects.hash(participantsCount, minimumAge, maximumAge);
	}
}
