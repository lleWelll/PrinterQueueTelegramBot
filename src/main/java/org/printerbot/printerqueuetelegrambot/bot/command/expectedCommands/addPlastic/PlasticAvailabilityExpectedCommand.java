package org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.addPlastic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PlasticAvailabilityExpectedCommand implements ExpectedCommand {

	private final PlasticSessionManager plasticSessionManager;

	private final BotStateStorage botStateStorage;

	@Override
	public SendMessage apply(Update update) {
		Long chatId = getChatId(update);
		String availability = update.getMessage().getText().trim();

		try {
			plasticSessionManager.addAvailability(chatId, parseAvailability(availability));
		} catch (IllegalArgumentException e) {
			log.error("Error occurred when parsing String -> boolean: {}", e.getMessage());
			return createSyntaxErrorMessage(update, "true or false");
		}

		botStateStorage.setState(chatId, BotState.WAITING_PLASTIC_DESCRIPTION);
		return createSendMessage(update, ConstantMessages.ADDPLASTIC_DESCRIPTION.getMessage());
	}
	boolean parseAvailability(String value) {
		return switch (value.toLowerCase()) {
			case "true" -> true;
			case "false" -> false;
			default -> throw new IllegalArgumentException("Undefined availability: " + value);
		};
	}
}
