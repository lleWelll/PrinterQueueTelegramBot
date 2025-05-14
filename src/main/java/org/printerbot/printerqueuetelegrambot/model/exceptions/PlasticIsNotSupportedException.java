package org.printerbot.printerqueuetelegrambot.model.exceptions;

public class PlasticIsNotSupportedException extends RuntimeException {
	public PlasticIsNotSupportedException() {
	}

	public PlasticIsNotSupportedException(String message) {
		super(message);
	}

	public PlasticIsNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlasticIsNotSupportedException(Throwable cause) {
		super(cause);
	}

	public PlasticIsNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
