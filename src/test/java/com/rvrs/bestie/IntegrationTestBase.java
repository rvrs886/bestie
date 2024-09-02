package com.rvrs.bestie;

import com.rvrs.bestie.core.participate.repo.ParticipateRequestsRepository;
import com.rvrs.bestie.core.scheduledevents.repo.ScheduledEventRepository;
import com.rvrs.bestie.core.scheduledevents.repo.ScheduledEventTaskRepository;
import com.rvrs.bestie.core.scheduledevents.service.ScheduledEventService;
import com.rvrs.bestie.security.repo.UserRepository;
import com.rvrs.bestie.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class IntegrationTestBase {

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected UserService userService;

	@Autowired
	protected ScheduledEventRepository scheduledEventRepository;

	@Autowired
	protected ScheduledEventTaskRepository scheduledEventTaskRepository;

	@Autowired
	protected ScheduledEventService scheduledEventService;

	@Autowired
	protected ParticipateRequestsRepository participateRequestsRepository;

}
