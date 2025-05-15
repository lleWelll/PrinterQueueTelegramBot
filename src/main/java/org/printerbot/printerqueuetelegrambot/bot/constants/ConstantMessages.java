package org.printerbot.printerqueuetelegrambot.bot.constants;

public enum ConstantMessages {

	HELLO_MESSAGE("Hello %s! I'm a bot for signing up for a 3D printer queue.\n\nAvailable commands:\n/join - Join the Queue\n/leave - Leave the queue\n/queue - Show all queue\n/myposition - Show your current position in queue\n/info - Get Info about available printers, plastic, admins and authors"),
	ERROR("Oops, something went wrong, try again"),

	SELECT_PRINTER_MESSAGE("Select one of these printers:"),

	SELECT_PLASTIC_MESSAGE("Choose available plastic:"),

	CHOOSE_CONFIRMATION_MESSAGE("You chose %s"),

	CONFIRM_JOIN_MESSAGE("Confirmation of queue:\n" +
			"Printer: %s\n" +
			"Plastic: %s\n" +
			"Uploaded model: %s\n" +
			"Join queue?"),

	QUEUE_JOIN_CONFIRMATION("Done! You are in queue now\n" +
			"To view your place enter /myposition\n" +
			"To view all queue enter /queue\n" +
			"To leave queue enter /leave"),

	QUEUE_JOIN_CANCELED("Okay, your joining queue is canceled"),


	LEAVE_COMMAND_MESSAGE("You have %d %s in queue\nChoose what entry you want to cancel:"),

	NO_QUEUE_ENTRIES_MESSAGE("You don't have any entries in queue"),

	CONFIRM_LEAVE_MESSAGE("You want to leave your queue?:\n%s"),

	LEAVE_CONFIRMATION_MESSAGE("Good, your queue entry is canceled"),

	QUEUE_LEAVE_CANCELED("Okay, your leaving is canceled"),

	YOUR_POSITION_MESSAGE("Your position in queue: "),

	INFO("%s - %s"),

	AUTHOR_INFO("Author: @lleWelll"),

	GITHUB_INFO("Github: https://github.com/lleWelll/PrinterQueueTelegramBot"),

	ADMINS_INFO("Admins:\n"),

	UPLOAD_STL_FILE_MESSAGE("Please upload file of model in '.stl' format"),

	FILE_FORMAT_IS_NOT_SUPPORTED_MESSAGE("File %s is not supported, please provide in '.stl' format"),

	TRY_TO_UPLOAD_FILE_AGAIN("Upload file again"),

	UNKNOWN_COMMAND_MESSAGE("Sorry, I don't know this command"),

	UNKNOWN_DOCUMENT_MESSAGE("Provided Document format is now supported, please provide '.stl' file");

	private final String message;

	ConstantMessages(String message) {
		this.message = message;
	}

	public String getFormattedMessage(Object... args) {
		return String.format(message, args);
	}
}
