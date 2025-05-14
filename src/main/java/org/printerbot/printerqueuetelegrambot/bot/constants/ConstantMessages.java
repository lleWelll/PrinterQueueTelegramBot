package org.printerbot.printerqueuetelegrambot.bot.constants;

public enum ConstantMessages {

	HELLO_MESSAGE("Hello %s! Nice to meet you!"),

	SELECT_PRINTER_MESSAGE("Okay, select one of these printers"),

	PRINTER_CONFIRMATION_MESSAGE("You chose %s %s"),

	ENTER_COMMAND_MESSAGE("Pls enter command %s to continue"),

	SELECT_PLASTIC_MESSAGE("Now choose available plastic"),

	PLASTIC_CONFIRMATION_MESSAGE("You chose %s %s %s"),

	PRINTER_IS_UNAVAILABLE_MESSAGE("Sorry, printer %s is not available right now, pls choose another or try later"),

	PLASTIC_IS_UNAVAILABLE_MESSAGE("Sorry, this plastic %s is not available right now, pls choose another or try later"),

	CHOSEN_MORE_PLASTIC_THAN_PRINTER_CAPACITY_MESSAGE("Sorry, you chose more plastic than printer capacity (chosen=%s, max=%s)"),

	QUEUE_JOIN_CONFIRMATION("Done! You are in queue now\n" +
			"To view your place enter /myplace\n" +
			"To view all queue enter /queue\n" +
			"To leave queue enter /leave"),

	QUEUE_JOIN_ERROR("Oops, something went wrong, try again"),

	UNKNOWN_COMMAND_MESSAGE("Sorry, I don't know this command");

	private final String message;

	ConstantMessages(String message) {
		this.message = message;
	}

	public String getFormattedMessage(Object... args) {
		return String.format(message, args);
	}
}
