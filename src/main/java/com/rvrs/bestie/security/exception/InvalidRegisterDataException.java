package com.rvrs.bestie.security.exception;

import com.rvrs.bestie.util.RuntimeExceptionWithErrorCode;

public class InvalidRegisterDataException extends RuntimeExceptionWithErrorCode {

	public InvalidRegisterDataException(String message) {
		super(message);
	}

	@Override
	public String getErrorCode() {
		return "INVALID_REGISTER_DATA";
	}
}
