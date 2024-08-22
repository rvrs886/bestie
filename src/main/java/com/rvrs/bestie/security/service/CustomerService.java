package com.rvrs.bestie.security.service;

import com.rvrs.bestie.security.domain.Customer;
import com.rvrs.bestie.security.domain.CustomerUserDetails;
import com.rvrs.bestie.security.exception.UserNotFoundException;
import com.rvrs.bestie.security.repo.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements UserDetailsService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("Could not find customer for given data"));

		return new CustomerUserDetails(customer);
	}
}
