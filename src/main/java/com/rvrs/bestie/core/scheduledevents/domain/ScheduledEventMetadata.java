package com.rvrs.bestie.core.scheduledevents.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ScheduledEventMetadata {

	private Integer participantsCount;

	private Integer minimumAge;

	private Integer maximumAge;

	private ScheduledEventMetadata() {}

	public ScheduledEventMetadata(Integer participantsCount, Integer minimumAge, Integer maximumAge) {
		this.participantsCount = participantsCount;
		this.minimumAge = minimumAge;
		this.maximumAge = maximumAge;
	}

	public Integer getParticipantsCount() {
		return participantsCount;
	}

	public Integer getMinimumAge() {
		return minimumAge;
	}

	public Integer getMaximumAge() {
		return maximumAge;
	}

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
