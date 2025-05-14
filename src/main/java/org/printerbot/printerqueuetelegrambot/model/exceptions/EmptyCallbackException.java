package org.printerbot.printerqueuetelegrambot.model.exceptions;

public class EmptyCallbackException extends RuntimeException {
	public EmptyCallbackException() {
	}

	public EmptyCallbackException(String message) {
		super(message);
	}

	public EmptyCallbackException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyCallbackException(Throwable cause) {
		super(cause);
	}

	public EmptyCallbackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
