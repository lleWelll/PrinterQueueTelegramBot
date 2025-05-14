package org.printerbot.printerqueuetelegrambot.model.exceptions;

public class PlasticNotFoundException extends RuntimeException{
	public PlasticNotFoundException() {
	}

	public PlasticNotFoundException(String message) {
		super(message);
	}

	public PlasticNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlasticNotFoundException(Throwable cause) {
		super(cause);
	}

	public PlasticNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
