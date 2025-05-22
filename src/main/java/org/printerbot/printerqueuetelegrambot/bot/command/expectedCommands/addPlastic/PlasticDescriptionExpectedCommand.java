package org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.addPlastic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.ExpectedCommand;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.printerbot.printerqueuetelegrambot.bot.util.PlasticSessionManager;
import org.printerbot.printerqueuetelegrambot.model.exceptions.PlasticNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PlasticDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlasticDescriptionExpectedCommand implements ExpectedCommand {

	private final BotStateStorage botStateStorage;

	private final PlasticSessionManager plasticSessionManager;

	private final PlasticDaoService plasticDaoService;

	@Override
	public SendMessage apply(Update update) {
		Long chatId = getChatId(update);
		String description = update.getMessage().getText().trim();
		plasticSessionManager.addDescription(chatId, description);

		try {
			plasticDaoService.save(plasticSessionManager.getSession(chatId));
			plasticSessionManager.deleteSession(chatId);
		} catch (Exception e) {
			log.error("Error occurred: {}", e.getMessage());
			return createSendMessage(update, ConstantMessages.ERROR.getMessage());
		}

		botStateStorage.setState(chatId, BotState.NONE);
		return createSendMessage(update, ConstantMessages.ADD_PLASTIC_CONFIRMATION_MESSAGE.getMessage());
	}
}
