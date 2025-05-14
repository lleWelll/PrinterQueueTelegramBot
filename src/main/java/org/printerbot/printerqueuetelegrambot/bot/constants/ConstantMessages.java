package org.printerbot.printerqueuetelegrambot.bot.constants;

public enum ConstantMessages {

	HELLO_MESSAGE("Hello %s! Nice to meet you!"),

	SELECT_PRINTER_MESSAGE("Okay, select one of these printers"),

	UNKNOWN_COMMAND_MESSAGE("Sorry, I don't know this command");

	private final String message;

	ConstantMessages(String message) {
		this.message = message;
	}

	public String getFormattedMessage(Object... args) {
		return String.format(message, args);
	}
}
