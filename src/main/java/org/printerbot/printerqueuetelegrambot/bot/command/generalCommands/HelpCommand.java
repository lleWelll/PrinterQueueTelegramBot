package org.printerbot.printerqueuetelegrambot.bot.command.generalCommands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpCommand implements GeneralCommand {
	@Override
	public SendMessage apply(Update update) {
		return null; //Будет выводиться список всех доступных команд и их описание
	}
}
