package com.rvrs.bestie.security.util;

import com.rvrs.bestie.security.domain.CustomUserDetails;
import com.rvrs.bestie.security.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

	public static CustomUserDetails getCurrentUser() {
		return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
