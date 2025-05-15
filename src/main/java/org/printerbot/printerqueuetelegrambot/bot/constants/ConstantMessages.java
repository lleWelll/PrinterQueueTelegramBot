package org.printerbot.printerqueuetelegrambot.bot.constants;

public enum ConstantMessages {

	HELLO_MESSAGE("Hello %s! I'm a bot for signing up for a 3D printer queue.\n\nAvailable commands:\n/join - Join the Queue\n/leave - Leave the queue\n/queue - Show all queue\n/myposition - Show your current position in queue"),

	SELECT_PRINTER_MESSAGE("Select one of these printers:"),

	CHOOSE_CONFIRMATION_MESSAGE("You chose %s"),

	SELECT_PLASTIC_MESSAGE("Now choose available plastic:"),

	CONFIRM_JOIN_MESSAGE("Confirmation of queue:\nPrinter: %s\nPlastic: %s\nJoin queue?"),

	QUEUE_JOIN_CONFIRMATION("Done! You are in queue now\n" +
			"To view your place enter /myposition\n" +
			"To view all queue enter /queue\n" +
			"To leave queue enter /leave"),

	QUEUE_JOIN_CANCELED("Okay, your joining queue is canceled"),

	ERROR("Oops, something went wrong, try again"),

	LEAVE_COMMAND_MESSAGE("You have %d %s in queue\nChoose what entry you want to cancel:"),

	NO_QUEUE_ENTRIES_MESSAGE("You don't have any entries in queue"),

	CONFIRM_LEAVE_MESSAGE("You want to leave your queue?:\n%s"),

	LEAVE_CONFIRMATION_MESSAGE("Good, your queue entry is canceled"),

	QUEUE_LEAVE_CANCELED("Okay, your leaving is canceled"),

	YOUR_POSITION_MESSAGE("Your position in queue: "),

	GET_DESCRIPTION("%s - %s"),

	UNKNOWN_COMMAND_MESSAGE("Sorry, I don't know this command");

	private final String message;

	ConstantMessages(String message) {
		this.message = message;
	}

	public String getFormattedMessage(Object... args) {
		return String.format(message, args);
	}
}
