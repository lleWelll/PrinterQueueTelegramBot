package org.printerbot.printerqueuetelegrambot.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnknownCommand implements Command {
	@Override
	public SendMessage apply(Update update) {
		String answer = "Sorry, I don't know this command";
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(String.valueOf(getChatId(update)));
		sendMessage.setText(answer);
		return sendMessage;
	}
}
