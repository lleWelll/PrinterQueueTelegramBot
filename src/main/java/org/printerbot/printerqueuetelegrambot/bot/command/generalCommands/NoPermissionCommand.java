package org.printerbot.printerqueuetelegrambot.bot.command.generalCommands;

import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class NoPermissionCommand implements GeneralCommand {
	@Override
	public SendMessage apply(Update update) {
		return createSendMessage(update, ConstantMessages.NOT_ENOUGH_RIGHTS_MESSAGE.getMessage());
	}
}
