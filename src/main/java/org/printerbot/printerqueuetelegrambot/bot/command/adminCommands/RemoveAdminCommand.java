package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.config.WhiteList;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.model.exceptions.NotEnoughPermissionsException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class RemoveAdminCommand implements AdminCommand {

	private final WhiteList whiteList;

	@Override
	public SendMessage apply(Update update) {
		log.info("Removing admin from whitelist");
		String username;
		try {
			username = splitMessage(update, 2)[1];
			if (whiteList.removeAdmin(username)) {
				return createSendMessage(update, ConstantMessages.REMOVE_ADMIN_CONFIRMATION_MESSAGE.getMessage(username));
			} else {
				return createSendMessage(update, ConstantMessages.REMOVE_ADMIN_ERROR.getMessage());
			}
		} catch (IndexOutOfBoundsException e) {
			log.error(e.getMessage());
			return createSyntaxErrorMessage(update, ConstantMessages.REMOVE_ADMIN_SYNTAX.getMessage());
		} catch (NotEnoughPermissionsException e) {
			log.error(e.getMessage());
			return createSendMessage(update, ConstantMessages.NOT_ENOUGH_RIGHTS_TO_REMOVE_MAIN_ADMIN.getMessage());
		}
	}
}
