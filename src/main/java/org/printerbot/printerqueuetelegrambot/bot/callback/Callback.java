package org.printerbot.printerqueuetelegrambot.bot.callback;

import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Callback {
	SendMessage apply(String data, Update update);

	default Long getChatId(Update update) {
		return update.getCallbackQuery().getMessage().getChatId();
	}

	default String getChatUsername(Update update) {
		return update.getCallbackQuery().getFrom().getUserName();
	}

	default SendMessage createSendMessage(Update update, String answer) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(String.valueOf(getChatId(update)));
		sendMessage.setText(answer);
		return sendMessage;
	}

	default SendMessage createErrorMessage(Update update) {
		return createSendMessage(update, ConstantMessages.ERROR.getMessage());
	}
}
