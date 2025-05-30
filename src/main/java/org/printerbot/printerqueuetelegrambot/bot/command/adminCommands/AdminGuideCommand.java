package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AdminGuideCommand implements AdminCommand{

	@Override
	public SendMessage apply(Update update) {
		return createSendMessage(update, ConstantMessages.ADMIN_GUIDE.getMessage());
	}
}
