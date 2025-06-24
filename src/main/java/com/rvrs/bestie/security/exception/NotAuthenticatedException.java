package com.rvrs.bestie.security.exception;

import com.rvrs.bestie.util.BestieException;

public class NotAuthenticatedException extends BestieException {

	public final String ERROR_CODE = "NOT_AUTHENTICATED";

	public NotAuthenticatedException(String message) {
		super(message);
	}

	public String getErrorCode() {
		return ERROR_CODE;
	}
}
