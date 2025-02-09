package com.rvrs.bestie.security.api;

import com.rvrs.bestie.security.domain.User;
import com.rvrs.bestie.security.dto.LoginDto;
import com.rvrs.bestie.security.repo.UserRepository;
import com.rvrs.bestie.security.service.SecurityService;
import com.rvrs.bestie.security.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SecurityController {

	private static final Logger log = LoggerFactory.getLogger(SecurityController.class);
	private final SecurityService securityService;
	private final UserRepository userRepository;

	public SecurityController(SecurityService securityService, UserRepository userRepository) {
		this.securityService = securityService;
		this.userRepository = userRepository;
	}

	@PostMapping("/login")
	public void login(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
		securityService.authenticate(loginDto, request, response);
	}

	@GetMapping("/current-user")
	public User getCurrentUser() {
		User user = SecurityUtils.getCurrentUser();
		return userRepository.findByUsername(user.getUsername())
				.orElseThrow(IllegalArgumentException::new);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public void handleIllegalArgumentException(IllegalArgumentException ex) {
		log.warn("Invalid username or password: {}", ex.getMessage());
	}

}
