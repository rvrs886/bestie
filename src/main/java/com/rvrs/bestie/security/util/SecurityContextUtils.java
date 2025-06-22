package com.rvrs.bestie.security.util;

import com.rvrs.bestie.security.domain.CustomUserDetails;
import com.rvrs.bestie.security.exception.NotAuthenticatedException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityContextUtils {

	public static CustomUserDetails getCurrentAuthentication() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (Objects.isNull(principal)) {
			throw new NotAuthenticatedException("Current session is not authenticated");
		}
		return (CustomUserDetails) principal;
	}
}
