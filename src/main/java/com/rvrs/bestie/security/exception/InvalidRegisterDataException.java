package com.rvrs.bestie.security.exception;

import com.rvrs.bestie.util.BestieException;

public class InvalidRegisterDataException extends BestieException {

	public final String ERROR_CODE = "INVALID_REGISTER_DATA";

	public InvalidRegisterDataException(String message) {
		super(message);
	}

	@Override
	public String getErrorCode() {
		return ERROR_CODE;
	}
}
