package org.printerbot.printerqueuetelegrambot.bot.document;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface DocumentCommand {

	SendMessage apply(Update update, File file);

	default Long getChatId(Update update) {
		return update.getMessage().getChatId();
	}

	default String getFileName(Update update) {
		return update.getMessage().getDocument().getFileName();
	}

	default String getChatUsername(Update update) {
		return update.getMessage().getChat().getUserName();
	}

	default SendMessage createSendMessage(Update update, String answer) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(String.valueOf(getChatId(update)));
		sendMessage.setText(answer);
		sendMessage.setParseMode("HTML");
		return sendMessage;
	}
}
