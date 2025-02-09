package com.rvrs.bestie.util;

public abstract class BestieException extends RuntimeException {

	public BestieException(String message) {
		super(message);
	}

	public abstract String getErrorCode();
}
