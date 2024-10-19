package com.rvrs.bestie.security.util;

import com.rvrs.bestie.security.dto.RegisterCredentials;
import com.rvrs.bestie.security.repo.UserRepository;

public class RegisterDataValidator {

	private final UserRepository userRepository;

	public RegisterDataValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void validateRegisterCredentials(RegisterCredentials registerCredentials) {

	}
}
