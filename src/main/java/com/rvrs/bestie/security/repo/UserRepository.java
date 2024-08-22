package com.rvrs.bestie.security.repo;

import com.rvrs.bestie.security.domain.Customer;
import com.rvrs.bestie.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByUsername(String username);

}
