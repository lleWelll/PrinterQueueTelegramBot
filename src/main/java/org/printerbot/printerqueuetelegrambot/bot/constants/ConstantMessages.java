package org.printerbot.printerqueuetelegrambot.bot.constants;

public enum ConstantMessages {

	// Greetings and Help
	HELLO_MESSAGE("Hello %s. I am a bot to manage the 3D printer queue.\n\n"),

	ERROR("Oops, something went wrong. Please try again."),
	ADD_ADMIN_ERROR("Something went wrong, maybe this user is already admin"),
	REMOVE_ADMIN_ERROR("Something went wrong, maybe this user is not an admin"),

	// Printer and Plastic Selection
	SELECT_PRINTER_MESSAGE("Select a printer:"),
	SELECT_PLASTIC_MESSAGE("Select a plastic type (Maximum for chosen printer - %s):"),
	CHOOSE_CONFIRMATION_MESSAGE("You selected %s."),
	CHOOSE_PRINTER_MODIFY_PROPERTY("Select what property to modify:"),
	ADD_ADMIN_CONFIRMATION_MESSAGE("New admin '%s' added to whitelist"),
	REMOVE_ADMIN_CONFIRMATION_MESSAGE("Admin '%s' removed from whitelist"),

	// Join Confirmation
	CONFIRM_JOIN_MESSAGE("Confirm joining the queue:\n" +
			"Printer: %s\n" +
			"Plastic: %s\n" +
			"Uploaded model: %s\n\n" +
			"Proceed?"),
	QUEUE_JOIN_CONFIRMATION("You have joined the queue.\n" +
			"Use /myposition to see your position.\n" +
			"Use /queue to see the full queue.\n" +
			"Use /leave to leave the queue."),
	QUEUE_JOIN_CANCELED("Join request canceled."),

	// Leave Confirmation
	LEAVE_COMMAND_MESSAGE("You have <b>%d</b> %s in the queue.\n" +
			"Enter the number of the entry to cancel:"),
	NO_QUEUE_ENTRIES_MESSAGE("You have no entries in the queue."),
	CONFIRM_LEAVE_MESSAGE("Confirm removal of this entry?\n%s"),
	LEAVE_CONFIRMATION_MESSAGE("Your queue entry has been removed."),
	QUEUE_LEAVE_CANCELED("Leave request canceled."),
	REMOVING_CONFIRMATION_MESSAGE("Queue entry with id '%s' removed successfully."),

	// Queue Status
	YOUR_POSITION_MESSAGE("Your position in the queue:"),
	NEXT_COMMAND_CONFIRMATION_MESSAGE("Next in queue:"),
	QUEUE_IS_EMPTY_MESSAGE("The queue is empty."),

	// Information
	INFO("%s - %s"),
	AUTHOR_INFO("<b>Author:</b> @llewelll"),
	GITHUB_INFO("<b>GitHub:</b> https://github.com/lleWelll/PrinterQueueTelegramBot"),
	ADMINS_INFO("<b>Admins:</b>\n"),

	// File Upload
	UPLOAD_STL_FILE_MESSAGE("Please upload a model file in <b>'.stl'</b> format."),
	FILE_FORMAT_IS_NOT_SUPPORTED_MESSAGE("File %s is not supported. <b>'.stl'</b> format."),
	TRY_TO_UPLOAD_FILE_AGAIN("Please upload the file again."),

	// Commands Syntax
	INCORRECT_COMMAND_SYNTAX_MESSAGE("Incorrect command.\n%s"),
	SET_AVAILABILITY_SYNTAX("/setavailability {printer|plastic} {id} {true|false}\n" +
			"Example: /setavailability printer 1 true"),
	ARCHIVED_FILE_SYNTAX("/archivedfile {archivedqueueid}\n" +
			"Example: /archivedfile 1"),
	FILE_SYNTAX("/file {queueid}\n" +
			"Example: /file 1"),
	MESSAGE_COMMAND_SYNTAX_MESSAGE("/message {queueid} {text}\n" +
			"Example: /message 1 Your model is ready."),
	REMOVE_QUEUE_SYNTAX_MESSAGE("/remove {queueid}\n" +
			"Example: /remove 1"),
	REMOVE_PRINTER_SYNTAX("/removeprinter {printerid}\n" +
			"Example: /removeprinter 1"),
	REMOVE_PLASTIC_SYNTAX("/removeplastic {plasticid}\n" +
			"Example: /removeplastic 1"),
	ADD_ADMIN_SYNTAX("/addadmin {username} (you should add user's tag without @)\nExample: /addadmin llewelll"),
	REMOVE_ADMIN_SYNTAX("/removeadmin {username} (you should add user's tag without @)\nExample: /removeadmin llewelll"),

	// Modify Entities
	MODIFY_PRINTER_SYNTAX("/modifyprinter {printerid}\n" +
			"Example: /modifyprinter 1"),
	MODIFY_PLASTIC_SYNTAX("/modifyplastic {plasticid}\n" +
			"Example: /modifyplastic 1"),
	REMOVE_PRINTER_CONFIRMATION_MESSAGE("Printer successfully removed."),
	REMOVE_PLASTIC_CONFIRMATION_MESSAGE("Plastic successfully removed."),
	MODIFY_PRINTER_CONFIRMATION_MESSAGE("Printer successfully modified."),
	MODIFY_PLASTIC_CONFIRMATION_MESSAGE("Plastic successfully modified."),

	// Add Entities
	ADD_PRINTER_BRAND_MESSAGE("Enter printer brand (e.g., 'Bambulab'):"),
	ADD_PRINTER_MODEL_MESSAGE("Enter printer model (e.g., 'X1-C'):"),
	ADD_PRINTER_FEATURES_MESSAGE("Enter printer features:"),
	ADD_PRINTER_MAX_PLASTIC_MESSAGE("Enter printer max plastic capacity (e.g., 2):"),
	ADD_PRINTER_SUPPORTED_PLASTIC_MESSAGE("Enter supported plastic IDs (comma-separated, e.g., 1,2,3):"),
	ADD_PRINTER_AVAILABILITY_MESSAGE("Enter printer availability ('true' or 'false'):"),
	ADD_PRINTER_CONFIRMATION_MESSAGE("Printer successfully added."),

	ADD_PLASTIC_BRAND_MESSAGE("Enter plastic brand (e.g., 'Bambulab'):"),
	ADD_PLASTIC_TYPE_MESSAGE("Enter plastic type (e.g., 'PLA'):"),
	ADD_PLASTIC_COLOR_MESSAGE("Enter plastic color (e.g., 'RED'):"),
	ADD_PLASTIC_AVAILABILITY_MESSAGE("Enter plastic availability ('true' or 'false'):"),
	ADD_PLASTIC_DESCRIPTION_MESSAGE("Enter plastic description:"),
	ADD_PLASTIC_CONFIRMATION_MESSAGE("Plastic successfully added."),

	ADDING_CANCELED("Canceled"),

	// Availability Update
	NEW_AVAILABILITY_SET_SUCCESSFULLY("Availability updated successfully."),

	// Rights and Errors
	NOT_ENOUGH_RIGHTS_MESSAGE("You don't have permission to use this command."),
	NOT_ENOUGH_RIGHTS_TO_REMOVE_MAIN_ADMIN("You don't have permission to remove this admin"),

	// Archived File Retrieval
	GETTING_ARCHIVED_DOCUMENT("Retrieving archived file for queue: %s"),

	// File Retrieval from queue
	GETTING_DOCUMENT("Retrieving file for queue: %s"),

	// User Messages
	NOTIFY_USER("It's your turn to print, if you didn't upload your 'stl' file, contact with admin"),

	// Unknown Commands and Documents
	UNKNOWN_COMMAND_MESSAGE("Unknown command. Use /start to see available commands."),
	UNKNOWN_DOCUMENT_MESSAGE("Unsupported document format. Please upload a <b>'.stl'</b> file."),
	ALL_GENERAL_COMMANDS("<b>Available commands:</b>\n" +
			"/join - Join the queue\n" +
			"/leave - Leave the queue\n" +
			"/queue - Show entire queue\n" +
			"/myposition - Show your position in the queue\n" +
			"/info - Show information about printers, plastics, admins, and authors"),
	ALL_ADMINS_COMMANDS("<b>All admin commands:</b>\n" +
			"<b>Queue Management:</b>\n" +
			"	/printers - Shows full info about printer\n" +
			"	/plastic - Shows full info about plastic\n" +
			"	/archive - Shows all queue archive\n" +
			"	/allqueue - Shows full about queue\n" +
			"	/next - Returns first queue entry in selected printer, sends you uploaded file and notifies user about it's turn\n" +
			"	/remove - Removes specified queue entry\n" +

			"\n<b>Printers and Plastic Management:</b>\n" +
			"	/addprinter - Adds new printer in Database\n" +
			"	/removeprinter - Removes printer from Database\n" +
			"	/modifyprinter - Modify existing printer\n" +
			"	/addplastic - Adds new plastic in Database\n" +
			"	/removeplastic - Removes plastic from Database\n" +
			"	/setavailable - Changes availability of printer/plastic\n" +

			"\n<b>Other commands:</b>\n" +
			"	/guide - Sends you basic admin guide about to use this bot\n" +
			"	/arhivedfile - Sends you '.stl' file from specified archived queue entry\n" +
			"	/file - Sends you '.stl file from specified queue entry\n" +
			"	/message - Sends message to user in queue\n" +
			"	/addadmin - adds new admin in system\n" +
			"	/removeadmin - removes admin from system"
	),

	ADMIN_GUIDE("<b>Admin Guide:</b>\n" +
		"\n<b>Get Started</b>\n" +
		"To start using this bot you need:\n" +
		"	1. Add all plastic in system (/addplastic)\n" +
		"	2. Add all printers in system (/addprinter)\nNote that, you can edit printers later (/modifyprinter command)\n" +
		"\n<b>Database Structure:</b>\n" +
		"Database of bot consist of 4 main tables:\n" +
		"	1. printers - table with info about printers\n" +
		"	2. plastic - table with info about plastic\n" +
		"	3. queue - main table that contains queue data (username, printer, plastic and uploaded model)\n" +
		"	4. archived_queue - table that contains old queue entries (username, printer, plastic and uploaded model)\n" +
		"\n<b>Admin Commands:</b>\n" +
		"In some commands it needed to provide Ids from database, to see it use:\n" +
		"	/allqueue - to see all queue with id\n" +
		"	/archive - to see past queue with id\n" +
		"	/printers - to see all printers with id\n" +
		"	/plastic - to see all plastic with id\n" +
		"Command /next moves queue. It gets first queue entry from database.\n" +
		"If Printing status of first queue entry is 'PRINTING' this entry gets into archived_queue.\n" +
		"Also if first queue entry contains uploaded 'stl' model, command will send you this file.\n" +
		"\n" +
		"You can add or remove admins with commands /addadmin and /removeadmin\n" +
		"To see all commands use /start command"
	);

	private final String message;

	ConstantMessages(String message) {
		this.message = message;
	}

	public String getMessage(Object... args) {
		return String.format(message, args);
	}
}
