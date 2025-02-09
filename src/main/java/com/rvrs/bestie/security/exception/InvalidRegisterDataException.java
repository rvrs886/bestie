package com.rvrs.bestie.security.exception;

import com.rvrs.bestie.util.BestieException;

public class InvalidRegisterDataException extends BestieException {

	public InvalidRegisterDataException(String message) {
		super(message);
	}

	@Override
	public String getErrorCode() {
		return "INVALID_REGISTER_DATA";
	}
}
