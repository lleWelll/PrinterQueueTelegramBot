package org.printerbot.printerqueuetelegrambot.model.exceptions;

public class NotEnoughPermissionsException extends Exception {
	public NotEnoughPermissionsException() {
		super();
	}

	public NotEnoughPermissionsException(String message) {
		super(message);
	}

	public NotEnoughPermissionsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughPermissionsException(Throwable cause) {
		super(cause);
	}

	protected NotEnoughPermissionsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
