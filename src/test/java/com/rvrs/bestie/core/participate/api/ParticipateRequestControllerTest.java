package com.rvrs.bestie.core.participate.api;

import com.rvrs.bestie.core.CoreFunctionalityTestBase;
import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEvent;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static com.rvrs.bestie.core.participate.domain.RequestStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class ParticipateRequestControllerTest extends CoreFunctionalityTestBase {

	private static final String PARTICIPATE_REQUEST_PATH = "/participate-requests";

	@Test
	public void shouldReturnUsersParticipateRequests() throws Exception {
		//given
		ScheduledEvent event = existingScheduledEvent();
		ParticipateRequest request1 = prepareParticipateRequest(event);
		ParticipateRequest request2 = prepareParticipateRequest(event);

		//when
		mockMvc.perform(get(PARTICIPATE_REQUEST_PATH + "/self"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").isArray())
				.andExpect(jsonPath("$.content[0]").exists())
				.andExpect(jsonPath("$.content[0].id").value(request1.getId()))
				.andExpect(jsonPath("$.content[0].message").value(request1.getMessage()))
				.andExpect(jsonPath("$.content[0].requestStatus").value(request1.getRequestStatus().toString()))
				.andExpect(jsonPath("$.content[0].customer").exists())
				.andExpect(jsonPath("$.content[0].scheduledEvent.id").value(event.getId().toString()))
				.andExpect(jsonPath("$.content[1].id").value(request2.getId()))
				.andExpect(jsonPath("$.content[1].message").value(request2.getMessage()))
				.andExpect(jsonPath("$.content[1].requestStatus").value(request2.getRequestStatus().toString()))
				.andExpect(jsonPath("$.content[1].customer").exists())
				.andExpect(jsonPath("$.content[1].scheduledEvent.id").value(event.getId().toString()))
				.andExpect(jsonPath("$.content[2]").doesNotExist());
	}

	@Test
	public void shouldAcceptPendingParticipateRequest() throws Exception {
		//given
		ScheduledEvent event = existingScheduledEvent();
		ParticipateRequest request1 = prepareParticipateRequest(event);
		ParticipateRequest request2 = prepareParticipateRequest(event);

		//when
		mockMvc.perform(post(PARTICIPATE_REQUEST_PATH + "/" + request1.getId() + "/accept"))
				.andExpect(status().isOk());

		//then
		assertThat(request1.getRequestStatus()).isEqualTo(ACCEPTED);
		assertThat(request2.getRequestStatus()).isEqualTo(PENDING);
	}

	@Test
	public void shouldDeclinePendingParticipateRequest() throws Exception {
		//given
		ScheduledEvent event = existingScheduledEvent();
		ParticipateRequest request1 = prepareParticipateRequest(event);
		ParticipateRequest request2 = prepareParticipateRequest(event);

		//when
		mockMvc.perform(post(PARTICIPATE_REQUEST_PATH + "/" + request2.getId() + "/decline"))
				.andExpect(status().isOk());

		//then
		assertThat(request1.getRequestStatus()).isEqualTo(PENDING);
		assertThat(request2.getRequestStatus()).isEqualTo(DECLINED);
	}

	@Test
	public void shouldDeleteParticipateRequestAndDoNotDeleteAssociatedScheduledEvent() throws Exception {
		//given
		ScheduledEvent event = existingScheduledEvent();
		ParticipateRequest request = prepareParticipateRequest(event);

		//when
		mockMvc.perform(delete(PARTICIPATE_REQUEST_PATH + "/" + request.getId()))
				.andExpect(status().isOk());

		//then
		assertThat(participateRequestsRepository.findById(request.getId())).isEmpty();
		assertThat(scheduledEventRepository.findById(event.getId())).isPresent();
	}

}