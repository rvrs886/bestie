package com.rvrs.bestie.security.service;

import com.rvrs.bestie.security.domain.CustomUserDetails;
import com.rvrs.bestie.security.domain.User;
import com.rvrs.bestie.security.exception.UserNotFoundException;
import com.rvrs.bestie.security.repo.UserRepository;
import com.rvrs.bestie.security.util.CredentialsValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("Could not find customer for given data"));
		return new CustomUserDetails(user);
	}

	public void registerUser(User user) {
		userRepository.save(user);
	}
}
