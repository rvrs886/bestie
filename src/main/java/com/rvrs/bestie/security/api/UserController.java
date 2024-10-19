package com.rvrs.bestie.security.api;

import com.rvrs.bestie.security.domain.Customer;
import com.rvrs.bestie.security.domain.User;
import com.rvrs.bestie.security.dto.CustomerDto;
import com.rvrs.bestie.security.service.SecurityService;
import com.rvrs.bestie.security.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final SecurityService securityService;

	public UserController(UserService userService, SecurityService securityService) {
		this.userService = userService;
		this.securityService = securityService;
	}

	@PostMapping("/register/customer")
	public void registerAccount(CustomerDto customerDto) {
		User user = new Customer(
				customerDto.registerCredentials().username(),
				customerDto.registerCredentials().password(),
				customerDto.personalInfo(),
				customerDto.image()
		);
		userService.registerUser();
	}
}
