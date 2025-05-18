package org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.addPlastic;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.ExpectedCommand;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.printerbot.printerqueuetelegrambot.bot.util.PlasticSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class PlasticBrandExpectedCommand implements ExpectedCommand {

	private final BotStateStorage botStateStorage;

	private final PlasticSessionManager plasticSessionManager;

	@Override
	public SendMessage apply(Update update) {
		Long chatId = getChatId(update);
		String brand = update.getMessage().getText().trim();
		plasticSessionManager.createNewSession(chatId);
		plasticSessionManager.addBrand(chatId, brand);
		botStateStorage.setState(chatId, BotState.WAITING_PLASTIC_TYPE);
		return createSendMessage(update, ConstantMessages.ADDPLASTIC_TYPE_MESSAGE.getMessage());
	}
}
