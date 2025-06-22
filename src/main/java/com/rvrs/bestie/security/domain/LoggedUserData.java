package com.rvrs.bestie.security.domain;

public record LoggedUserData(String username) {

	public static LoggedUserData of(User user) {
		return new LoggedUserData(user.getUsername());
	}
}
