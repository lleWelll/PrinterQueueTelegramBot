package org.printerbot.printerqueuetelegrambot.bot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
	SendMessage apply(Update update);

	default Long getChatId(Update update) {
		return update.getMessage().getChatId();
	}

	default String getChatUsername(Update update) {
		return update.getMessage().getChat().getUserName();
	}
}
