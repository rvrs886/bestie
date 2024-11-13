package com.rvrs.bestie.util;

public abstract class RuntimeExceptionWithErrorCode extends RuntimeException {

	public RuntimeExceptionWithErrorCode(String message) {
		super(message);
	}

	public abstract String getErrorCode();
}
