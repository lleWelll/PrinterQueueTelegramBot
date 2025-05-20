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

	CONFIRM_LEAVE_MESSAGE("You want to leave your queue?\n%s"),

	LEAVE_CONFIRMATION_MESSAGE("Good, your queue entry is canceled"),

	QUEUE_LEAVE_CANCELED("Okay, your leaving is canceled"),

	YOUR_POSITION_MESSAGE("Your position in queue: "),

	INFO("%s - %s"),

	AUTHOR_INFO("Author: @llewelll"),

	GITHUB_INFO("Github: https://github.com/lleWelll/PrinterQueueTelegramBot"),

	ADMINS_INFO("Admins:\n"),

	UPLOAD_STL_FILE_MESSAGE("Please upload file of model in '.stl' format"),

	FILE_FORMAT_IS_NOT_SUPPORTED_MESSAGE("File %s is not supported, please provide in '.stl' format"),

	TRY_TO_UPLOAD_FILE_AGAIN("Upload file again"),

	INCORRECT_COMMAND_SYNTAX_MESSAGE("Incorrect command, please write: %s"),

	SETAVAILABILITY_COMMAND_SYNTAX_MESSAGE("/setavailability {printer/plastic} {id} {availability:true/false}\nFor example: /setavailability printer 1 true"),

	REMOVE_PRINTER_COMMAND_SYNTAX_MESSAGE("/removeprinter {printerId}\nFor example: /removeprinter 1"),

	REMOVE_PLASTIC_COMMAND_SYNTAX_MESSAGE("/removeplastic {plasticId}\nForExample: /removeplastic 1"),

	MODIFY_PRINTER_COMMAND_SYNTAX_MESSAGE("/modifyprinter {printerId}\nFor example /modifyprinter 1"),

	MODIFY_PLASTIC_COMMAND_SYNTAX_MESSAGE("/modifyplastic {plasticId}\nFor example /modifyplastic 1"),

	REMOVE_PRINTER_COMMAND_CONFIRMATION_MESSAGE("Printer removed successfully"),

	REMOVE_PLASTIC_COMMAND_CONFIRMATION_MESSAGE("Plastic removed successfully"),

	MODIFY_PRINTER_CONFIRMATION_MESSAGE("Printer modified successfully"),

	MODIFY_PLASTIC_CONFIRMATION_MESSAGE("Plastic modified successfully"),

	CHOOSE_PROPERTY_MESSAGE("Choose what property you want to change:"),

	ADDPRINTER_BRAND_MESSAGE("Enter printer brand, for example 'Bambulab'"),

	ADDPRINTER_MODEL_MESSAGE("Enter printer model, for example 'X1-C'"),

	ADDPRINTER_FEATUES_MESSAGE("Enter printer features"),

	ADDPRINTER_MAX_PLASTIC_MESSAGE("Enter printer's max plastic capacity, for example 2"),

	ADDPRINTER_SUPPORTED_PLASTIC_MESSAGE("Enter printer's supported plastic\nIt should plasticId and written in format: 1, 2, 3, 4"),

	ADD_PRINTER_AVAILABILITY_MESSAGE("Enter printer's availability, For example 'true'/'false"),

	ADDPRINTER_CONFIRMATION_MESSAGE("Printer added successfully"),

	ADDPLASTIC_BRAND_MESSAGE("Enter plastic brand\nFor example 'Bambulab'"),

	ADDPLASTIC_TYPE_MESSAGE("Enter plastic type\nFor example 'PLA'"),

	ADDPLASTIC_COLOR_MESSAGE("Enter plastic color\nFor example 'RED'"),

	ADDPLASTIC_AVAILABLE_MESSAGE("Enter plastics availability\nFor example 'true'/'false"),

	ADDPLASTIC_DESCRIPTION("Enter plastic description"),

	ADDPLASTIC_CONFIRMATION_MESSAGE("Plastic added successfully"),

	NEW_AVAILABILITY_SET_SUCCESSFULLY("New availability set successfully"),

	NOT_ENOUGH_RIGHTS_MESSAGE("Sorry, you don't have enough rights to use this command"),

	UNKNOWN_COMMAND_MESSAGE("Sorry, I don't know this command"),

	UNKNOWN_DOCUMENT_MESSAGE("Provided Document format is now supported, please provide '.stl' file");

	private final String message;

	ConstantMessages(String message) {
		this.message = message;
	}

	public String getMessage(Object... args) {
		return String.format(message, args);
	}
}
