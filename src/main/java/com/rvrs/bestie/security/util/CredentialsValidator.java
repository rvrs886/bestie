package com.rvrs.bestie.security.util;

import com.rvrs.bestie.security.domain.PersonalInfo;
import com.rvrs.bestie.security.dto.RegisterCredentials;
import com.rvrs.bestie.security.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CredentialsValidator {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public CredentialsValidator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public boolean credentialsValid(RegisterCredentials registerCredentials, PersonalInfo personalInfo) {
		return usernameValid(registerCredentials.username()) &&
				emailValid(personalInfo.getEmail()) &&
				passwordsMatches(registerCredentials.password(), registerCredentials.password());
	}

	private boolean usernameValid(String username) {
		return !userRepository.existsByUsername(username);
	}

	private boolean emailValid(String email) {
		return !userRepository.existsByPersonalInfoEmail(email);
	}

	private boolean passwordsMatches(String password, String repeatPassword) {
		return password.equals(repeatPassword);
	}
}
