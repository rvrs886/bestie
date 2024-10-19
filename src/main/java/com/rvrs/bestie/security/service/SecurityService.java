package com.rvrs.bestie.security.service;

import com.rvrs.bestie.security.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

	private final AuthenticationProvider daoAuthenticationProvider;

	private final SecurityContextRepository securityContextRepository;

	public SecurityService(AuthenticationProvider daoAuthenticationProvider, SecurityContextRepository securityContextRepository) {
		this.daoAuthenticationProvider = daoAuthenticationProvider;
		this.securityContextRepository = securityContextRepository;
	}


	public void authenticate(LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
		UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
				loginDto.username(), loginDto.password()
		);
		Authentication authentication = daoAuthenticationProvider.authenticate(token);
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
		securityContextRepository.saveContext(context, request, response);
	}

}
