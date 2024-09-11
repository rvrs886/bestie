package com.rvrs.bestie.core.scheduledevents.api;

import com.rvrs.bestie.core.CoreFunctionalityTestBase;
import com.rvrs.bestie.core.participate.domain.RequestStatus;
import com.rvrs.bestie.core.participate.dto.ParticipateRequestDto;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventData;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventMetadata;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventType;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventType.SPORT;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ScheduledEventControllerTest extends CoreFunctionalityTestBase {

	private static final String SCHEDULED_EVENTS_PATH = "/scheduled-events";

	@Test
	public void shouldReturnScheduledEvents() throws Exception {
		//given
		ScheduledEvent scheduledEvent1 = existingScheduledEvent();
		ScheduledEvent scheduledEvent2 = existingScheduledEvent();

		//when & then
		mockMvc.perform(get(SCHEDULED_EVENTS_PATH))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").isArray())
				.andExpect(jsonPath("$.content[0]").exists())
				.andExpect(jsonPath("$.content[0].id").value(scheduledEvent1.getId().toString()))
				.andExpect(jsonPath("$.content[1]").exists())
				.andExpect(jsonPath("$.content[1].id").value(scheduledEvent2.getId().toString()))
				.andExpect(jsonPath("$.content[2]").doesNotExist());
	}

	@Test
	public void shouldReturnScheduledEventById() throws Exception {
		//given
		ScheduledEvent scheduledEvent = existingScheduledEvent();

		//when & then
		mockMvc.perform(get(SCHEDULED_EVENTS_PATH + "/" + scheduledEvent.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(scheduledEvent.getId().toString()))
				.andExpect(jsonPath("$.scheduledEventData").exists())
				.andExpect(jsonPath("$.scheduledEventData.title")
						.value(scheduledEvent.getScheduledEventData().title()))
				.andExpect(jsonPath("$.scheduledEventData.description")
						.value(scheduledEvent.getScheduledEventData().description()))
				.andExpect(jsonPath("$.scheduledEventData.scheduledDateTime")
						.value(scheduledEvent.getScheduledEventData().scheduledDateTime().toString()))
				.andExpect(jsonPath("$.scheduledEventData.scheduledEventType")
						.value(scheduledEvent.getScheduledEventData().scheduledEventType().toString()))
				.andExpect(jsonPath("$.scheduledEventData.location.postCode")
						.value(scheduledEvent.getScheduledEventData().location().postCode()))
				.andExpect(jsonPath("$.scheduledEventData.location.city")
						.value(scheduledEvent.getScheduledEventData().location().city()))
				.andExpect(jsonPath("$.scheduledEventData.location.country")
						.value(scheduledEvent.getScheduledEventData().location().country()))
				.andExpect(jsonPath("$.scheduledEventData.location.address")
						.value(scheduledEvent.getScheduledEventData().location().address()))
				.andExpect(jsonPath("$.scheduledEventData.location.name")
						.value(scheduledEvent.getScheduledEventData().location().name()))
				.andExpect(jsonPath("$.scheduledEventData.scheduledEventMetadata.participantsCount")
						.value(scheduledEvent.getScheduledEventData().scheduledEventMetadata().participantsCount()))
				.andExpect(jsonPath("$.scheduledEventData.scheduledEventMetadata.minimumAge")
						.value(scheduledEvent.getScheduledEventData().scheduledEventMetadata().minimumAge()))
				.andExpect(jsonPath("$.scheduledEventData.scheduledEventMetadata.maximumAge")
						.value(scheduledEvent.getScheduledEventData().scheduledEventMetadata().maximumAge()));
	}

	@Test
	public void shouldGenerateScheduledEvent() throws Exception {
		//given
		ScheduledEventData scheduledEventData = new ScheduledEventData(
				"test title",
				"test description",
				testLocation(),
				now(),
				SPORT,
				new ScheduledEventMetadata(10, 18, 25)
		);

		//when
		mockMvc.perform(post(SCHEDULED_EVENTS_PATH)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(scheduledEventData)))
				.andExpect(status().isOk());

		//then
		List<ScheduledEvent> foundScheduledEvents = scheduledEventRepository.findAll();
		assertThat(foundScheduledEvents).hasSize(1);
		assertThat(foundScheduledEvents.getFirst().getScheduledEventData()).isEqualTo(scheduledEventData);
		assertThat(foundScheduledEvents.getFirst().getCreationDateTime()).isNotNull();
		assertThat(foundScheduledEvents.getFirst().getId()).isNotNull();
	}

	@Test
	public void shouldAddParticipateRequestForScheduledEvent() throws Exception {
		//given
		ScheduledEvent scheduledEvent = existingScheduledEvent();
		ParticipateRequestDto participateRequestDto = new ParticipateRequestDto("please accept my request");

		//when
		mockMvc.perform(post(SCHEDULED_EVENTS_PATH + "/" + scheduledEvent.getId() + "/request")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(participateRequestDto)))
				.andExpect(status().isOk());

		//then
		doInNewTransaction(() -> {
			ScheduledEvent updatedEventWithParticipateRequest = scheduledEventRepository.findById(scheduledEvent.getId()).get();
			assertThat(updatedEventWithParticipateRequest.getParticipateRequests()).hasSize(1);
			assertThat(updatedEventWithParticipateRequest.getParticipateRequests().getFirst().getMessage())
					.isEqualTo(participateRequestDto.message());
			assertThat(updatedEventWithParticipateRequest.getParticipateRequests().getFirst().getId())
					.isNotNull();
			assertThat(updatedEventWithParticipateRequest.getParticipateRequests().getFirst().getRequestStatus())
					.isEqualTo(RequestStatus.PENDING);
			assertThat(updatedEventWithParticipateRequest.getParticipateRequests().getFirst().getCustomer())
					.isNotNull();
		});
	}

}