package com.rvrs.bestie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvrs.bestie.core.participate.repo.ParticipateRequestsRepository;
import com.rvrs.bestie.core.scheduledevents.repo.ScheduledEventRepository;
import com.rvrs.bestie.core.scheduledevents.service.ScheduledEventService;
import com.rvrs.bestie.security.domain.Customer;
import com.rvrs.bestie.security.domain.User;
import com.rvrs.bestie.security.repo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW;

@SpringBootTest
@EnableAutoConfiguration
public abstract class IntegrationTestBase {

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected ScheduledEventRepository scheduledEventRepository;

	@Autowired
	protected ScheduledEventService scheduledEventService;

	@Autowired
	protected ParticipateRequestsRepository participateRequestsRepository;

	@Autowired
	protected ObjectMapper objectMapper;

	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	protected PlatformTransactionManager transactionManager;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		setupSecurityContext();
	}

	@AfterEach
	public void cleanup() {
		this.participateRequestsRepository.deleteAll();
		this.userRepository.deleteAll();
		this.scheduledEventRepository.deleteAll();
	}

	protected void doInNewTransaction(Runnable runnable) {
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.setPropagationBehavior(PROPAGATION_REQUIRES_NEW);
		transactionTemplate.execute(txn -> {
			runnable.run();
			return null;
		});
	}

	private void setupSecurityContext() {
		User user = userRepository.save(new Customer("testuser", "testuser", null, null));

		Authentication authentication = new UsernamePasswordAuthenticationToken(user, List.of());

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
