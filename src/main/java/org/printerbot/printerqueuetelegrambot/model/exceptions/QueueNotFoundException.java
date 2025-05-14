package org.printerbot.printerqueuetelegrambot.model.exceptions;

public class QueueNotFoundException extends RuntimeException {
	public QueueNotFoundException() {
	}

	public QueueNotFoundException(String message) {
		super(message);
	}

	public QueueNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public QueueNotFoundException(Throwable cause) {
		super(cause);
	}

	public QueueNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
