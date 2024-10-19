package com.rvrs.bestie.security.api;

import com.rvrs.bestie.security.dto.LoginDto;
import com.rvrs.bestie.security.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SecurityController {

	private final SecurityService securityService;

	public SecurityController(SecurityService securityService) {
		this.securityService = securityService;
	}

	@PostMapping("/login")
	public void login(LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
		securityService.authenticate(loginDto, request, response);
	}

}
