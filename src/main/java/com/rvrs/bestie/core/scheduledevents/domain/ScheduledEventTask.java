package com.rvrs.bestie.core.scheduledevents.domain;

import com.rvrs.bestie.core.scheduledevents.api.dto.ScheduledEventDto;
import com.rvrs.bestie.util.Clock;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.rvrs.bestie.core.scheduledevents.domain.GenerationStatus.ERROR;
import static com.rvrs.bestie.core.scheduledevents.domain.GenerationStatus.GENERATED;
import static java.util.Objects.isNull;

@Table
@Entity(name = "scheduled_event_tasks")
public class ScheduledEventTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GenerationStatus generationStatus = GenerationStatus.PENDING;

	private String errorMessage;

	@Embedded
	private ScheduledEventDto scheduledEventData;

	private LocalDateTime generationTriggeredAt;

	private LocalDateTime generationFinishedAt;

	@OneToOne
	private ScheduledEvent generatedScheduledEvent;

	public ScheduledEventTask() {
	}

	public ScheduledEventTask(ScheduledEventDto scheduledEventData, Clock clock) {
		this.scheduledEventData = scheduledEventData;
		this.generationTriggeredAt = clock.now();
	}

	public void finish(ScheduledEvent scheduledEvent, String errorMessage, Clock clock) {
		if (isNull(scheduledEvent)) {
			this.generationStatus = ERROR;
			this.errorMessage = errorMessage;
			this.generationFinishedAt = clock.now();
		} else {
			this.generationStatus = GENERATED;
			this.generatedScheduledEvent = scheduledEvent;
			this.generationFinishedAt = clock.now();
		}
	}

	public Long getId() {
		return id;
	}

	public GenerationStatus getGenerationStatus() {
		return generationStatus;
	}

	public ScheduledEventDto getScheduledEventData() {
		return scheduledEventData;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public ScheduledEvent getGeneratedScheduledEvent() {
		return generatedScheduledEvent;
	}

	public LocalDateTime getGenerationTriggeredAt() {
		return generationTriggeredAt;
	}

	public LocalDateTime getGenerationFinishedAt() {
		return generationFinishedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ScheduledEventTask that = (ScheduledEventTask) o;
		return Objects.equals(id, that.id) &&
				generationStatus == that.generationStatus &&
				Objects.equals(errorMessage, that.errorMessage) &&
				Objects.equals(scheduledEventData, that.scheduledEventData) &&
				Objects.equals(generatedScheduledEvent, that.generatedScheduledEvent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, generationStatus, errorMessage, scheduledEventData, generatedScheduledEvent);
	}

	@Override
	public String toString() {
		return "ScheduledEventTask{" +
				"id=" + id +
				", generationStatus=" + generationStatus +
				", errorMessage='" + errorMessage + '\'' +
				", scheduledEventData=" + scheduledEventData +
				", scheduledEvent=" + generatedScheduledEvent +
				'}';
	}
}
