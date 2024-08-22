package com.rvrs.bestie.security.util;

import com.rvrs.bestie.security.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

	public static User getCurrentUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
