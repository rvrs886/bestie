package com.rvrs.bestie.security.repo;

import com.rvrs.bestie.security.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

	Optional<Customer> findByUsername(String username);

}
