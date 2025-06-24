package com.rvrs.bestie.security.exception;

import com.rvrs.bestie.util.BestieException;

public class UserNotFoundException extends BestieException {

	public final String ERROR_CODE = "USER_NOT_FOUND";

	public UserNotFoundException(String message) {
		super(message);
	}

	public String getErrorCode() {
		return ERROR_CODE;
	}
}
