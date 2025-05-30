package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.config.WhiteList;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddAdminCommand implements AdminCommand {

	private final WhiteList whiteList;

	@Override
	public SendMessage apply(Update update) {
		log.info("Adding new admin to whitelist");
		String username;
		try {
			username = splitMessage(update, 2)[1];
			if (whiteList.addNewAdmin(username)) {
				return createSendMessage(update, ConstantMessages.ADD_ADMIN_CONFIRMATION_MESSAGE.getMessage(username));
			}
			else {
				return createSendMessage(update, ConstantMessages.ADD_ADMIN_ERROR.getMessage());
			}
		} catch (IndexOutOfBoundsException e) {
			log.error(e.getMessage());
			return createSyntaxErrorMessage(update, ConstantMessages.ADD_ADMIN_SYNTAX.getMessage());
		}
	}
}
