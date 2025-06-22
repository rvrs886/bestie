package com.rvrs.bestie.security.exception;

public class NotAuthenticatedException extends RuntimeException {

	public final String ERROR_CODE = "NOT_AUTHENTICATED";

	public NotAuthenticatedException(String message) {
		super(message);
	}

	public String getErrorCode() {
		return ERROR_CODE;
	}
}
