package com.rvrs.bestie.core.scheduledevents.service;

import com.rvrs.bestie.core.CoreFunctionalityTestBase;
import com.rvrs.bestie.core.participate.dto.ParticipateRequestDto;
import com.rvrs.bestie.core.scheduledevents.domain.Location;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventType;
import org.junit.jupiter.api.Test;

import static com.rvrs.bestie.core.participate.domain.RequestStatus.PENDING;
import static com.rvrs.bestie.core.scheduledevents.service.ScheduledEventBuilder.scheduledEventBuilder;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

class ScheduledEventServiceTest extends CoreFunctionalityTestBase {

	@Test
	public void shouldReturnScheduledEvent() {
		//given
		ScheduledEvent event = existingScheduledEvent();

		doInNewTransaction(() -> {
			//when
			ScheduledEvent returnedEvent = scheduledEventService.getScheduledEventById(event.getId());

			//then
			assertThat(returnedEvent).usingRecursiveComparison().isEqualTo(event);
		});
	}

	@Test
	public void shouldAddParticipateRequestForGivenEvent() {
		//given
		ScheduledEvent event = existingScheduledEvent();
		ParticipateRequestDto participateRequestDto = new ParticipateRequestDto(
				"please accept my request :D"
		);

		//when
		scheduledEventService.addParticipateRequestForScheduledEvent(event.getId(), participateRequestDto);

		//then
		doInNewTransaction(() -> {
			ScheduledEvent updatedEvent = scheduledEventService.getScheduledEventById(event.getId());
			assertThat(updatedEvent.getParticipateRequests()).isNotEmpty();
			assertThat(updatedEvent.getParticipateRequests()).hasSize(1);
			assertThat(updatedEvent.getParticipateRequests().get(0).getMessage()).isEqualTo(participateRequestDto.message());
			assertThat(updatedEvent.getParticipateRequests().get(0).getRequestStatus()).isEqualTo(PENDING);
		});
	}

	@Test
	public void shouldCreateScheduledEventTaskConcurrently() {
		Location location = testLocation();
		ScheduledEvent event = scheduledEventBuilder()
				.withTitle("test title")
				.withDescription("test description")
				.withLocation(location)
				.withScheduledDateTime(of(2024, 9, 8, 12, 0))
				.withType(ScheduledEventType.ENTERTAINMENT)
				.build();

	}

}