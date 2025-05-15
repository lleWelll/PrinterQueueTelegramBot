package org.printerbot.printerqueuetelegrambot.bot.command.generalCommands;

import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommand implements GeneralCommand {
	@Override
	public SendMessage apply(Update update) {
		String username = getChatUsername(update);
		String answer = ConstantMessages.HELLO_MESSAGE.getFormattedMessage(username) +
				"\n\n" +
				ConstantMessages.AUTHOR_INFO.getFormattedMessage() +
				"\n\n" +
				ConstantMessages.GITHUB_INFO.getFormattedMessage();
		return createSendMessage(update, answer);
	}

}
