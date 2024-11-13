package com.rvrs.bestie.security.api;

import com.rvrs.bestie.security.domain.Customer;
import com.rvrs.bestie.security.domain.User;
import com.rvrs.bestie.security.dto.CustomerDto;
import com.rvrs.bestie.security.dto.LoginDto;
import com.rvrs.bestie.security.exception.InvalidRegisterDataException;
import com.rvrs.bestie.security.service.SecurityService;
import com.rvrs.bestie.security.service.UserService;
import com.rvrs.bestie.security.util.CredentialsValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.juli.logging.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final SecurityService securityService;
	private final CredentialsValidator credentialsValidator;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserService userService, SecurityService securityService, CredentialsValidator credentialsValidator, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.securityService = securityService;
		this.credentialsValidator = credentialsValidator;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/register/customer")
	public void registerAccount(@RequestBody @Valid CustomerDto customerDto, HttpServletRequest request, HttpServletResponse response) {
		boolean valid = credentialsValidator.credentialsValid(
				customerDto.registerCredentials(),
				customerDto.personalInfo()
		);

		if (!valid) {
			throw new InvalidRegisterDataException("Invalid register data");
		}

		LoginDto loginDto = new LoginDto(
				customerDto.registerCredentials().username(),
				customerDto.registerCredentials().password()
		);

		User user = new Customer(
				customerDto.registerCredentials().username(),
				passwordEncoder.encode(customerDto.registerCredentials().password()),
				customerDto.personalInfo(),
				customerDto.image()
		);

		userService.registerUser(user);

		securityService.authenticate(loginDto, request, response);
	}
}
