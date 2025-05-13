package org.printerbot.printerqueuetelegrambot.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommand implements Command {

	@Override
	public SendMessage apply(Update update) {
		Long chatId = getChatId(update);
		String username = getChatUsername(update);
		String answer = "Hi, " + username + ", nice to meet you!";
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(String.valueOf(chatId));
		sendMessage.setText(answer);
		return sendMessage;
	}

}
