package org.printerbot.printerqueuetelegrambot.bot.command.generalCommands;

import org.printerbot.printerqueuetelegrambot.bot.command.Command;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnknownCommand implements GeneralCommand {
	@Override
	public SendMessage apply(Update update) {
		String answer = ConstantMessages.UNKNOWN_COMMAND_MESSAGE.getFormattedMessage();
		return createSendMessage(update, answer);
	}
}
