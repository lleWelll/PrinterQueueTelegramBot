package org.printerbot.printerqueuetelegrambot.model.exceptions;

public class PrinterNotFoundException extends RuntimeException{
	public PrinterNotFoundException() {
	}

	public PrinterNotFoundException(String message) {
		super(message);
	}

	public PrinterNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrinterNotFoundException(Throwable cause) {
		super(cause);
	}

	public PrinterNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
