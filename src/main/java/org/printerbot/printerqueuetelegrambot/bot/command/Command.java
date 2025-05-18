package org.printerbot.printerqueuetelegrambot.bot.command;

import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
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

	default SendMessage createSendMessage(Update update, String answer) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(String.valueOf(getChatId(update)));
		sendMessage.setText(answer);
		return sendMessage;
	}

	default SendMessage createSyntaxErrorMessage(Update update, String syntaxMessage) {
		return createSendMessage(update, ConstantMessages.INCORRECT_COMMAND_SYNTAX_MESSAGE.getMessage(syntaxMessage.toLowerCase()));
	}

}
