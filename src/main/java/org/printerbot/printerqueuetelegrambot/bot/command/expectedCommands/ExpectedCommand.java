package org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands;

import org.printerbot.printerqueuetelegrambot.bot.command.Command;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.util.JsonHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public interface ExpectedCommand extends Command {

	default void addCancelKeyboard(SendMessage message) {
		List<List<InlineKeyboardButton>> rows = new ArrayList<>();
		rows.add(List.of(createCancelButton()));
		message.setReplyMarkup(new InlineKeyboardMarkup(rows));
	}

	private InlineKeyboardButton createCancelButton() {
		return InlineKeyboardButton.builder()
				.text("Cancel")
				.callbackData(JsonHandler.listToJson(List.of(CallbackType.CANCEL_ADD.toString(), "Cancel")))
				.build();
	}
}
