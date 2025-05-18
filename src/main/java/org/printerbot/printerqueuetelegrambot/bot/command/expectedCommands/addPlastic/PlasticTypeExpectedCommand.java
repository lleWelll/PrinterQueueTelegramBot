package org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.addPlastic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.ExpectedCommand;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.printerbot.printerqueuetelegrambot.bot.util.PlasticSessionManager;
import org.printerbot.printerqueuetelegrambot.model.enums.PlasticType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlasticTypeExpectedCommand implements ExpectedCommand {

	private final BotStateStorage botStateStorage;

	private final PlasticSessionManager plasticSessionManager;

	@Override
	public SendMessage apply(Update update) {
		Long chatId = getChatId(update);
		String type = update.getMessage().getText().trim().toUpperCase();
		try {
			plasticSessionManager.addType(chatId, PlasticType.valueOf(type));
		} catch (IllegalArgumentException e) {
			log.error("Error occurred when getting PlasticType.valueOf(String): {}", e.getMessage());
			return createSyntaxErrorMessage(update, "Another type. This type of plastic is not found");
		}
		botStateStorage.setState(chatId, BotState.WAITING_PLASTIC_COLOR);
		return createSendMessage(update, ConstantMessages.ADDPLASTIC_COLOR_MESSAGE.getMessage());
	}
}
