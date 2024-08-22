package com.rvrs.bestie.security.exception;

public class UserNotFoundException extends RuntimeException {

	public final String ERROR_CODE = "USER_NOT_FOUND";

	public UserNotFoundException(String message) {
		super(message);
	}

	public String getErrorCode() {
		return ERROR_CODE;
	}
}
