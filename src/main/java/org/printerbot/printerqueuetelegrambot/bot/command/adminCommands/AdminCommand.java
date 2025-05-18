package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import org.printerbot.printerqueuetelegrambot.bot.command.Command;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface AdminCommand extends Command {

	default String[] splitMessage(Update update, int limit) {
		return update.getMessage().getText().split("\\s++", limit);
	}

	default boolean parseAvailability(String value) {
		return switch (value.toLowerCase()) {
			case "true" -> true;
			case "false" -> false;
			default -> throw new IllegalArgumentException("Undefined availability: " + value);
		};
	}
}
