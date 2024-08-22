package com.rvrs.bestie.security.domain;

import jakarta.persistence.Column;

import java.util.Objects;

public class UserData {

	@Column(nullable = false, unique = true)
	protected final String username;

	@Column(nullable = false)
	protected final String password;

	public UserData(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserData userData = (UserData) o;
		return Objects.equals(username, userData.username) && Objects.equals(password, userData.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password);
	}

	@Override
	public String toString() {
		return "UserData{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
