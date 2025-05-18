package org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.addPrinter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.ExpectedCommand;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.printerbot.printerqueuetelegrambot.bot.util.PrinterSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddPrinterAvailabilityExpectedMessage implements ExpectedCommand {
	private final BotStateStorage botStateStorage;

	private final PrinterSessionManager printerSessionManager;

	@Override
	public SendMessage apply(Update update) {
		Long chatId = getChatId(update);
		String availability = update.getMessage().getText().trim();

		try {
			printerSessionManager.addAvailability(chatId, parseAvailability(availability));
		} catch (IllegalArgumentException e) {
			log.error("Error occurred when parsing String -> boolean: {}", e.getMessage());
			return createSyntaxErrorMessage(update, "true or false");
		}

		botStateStorage.setState(chatId, BotState.WAITING_PRINTER_MAX_PLASTIC_CAPACITY);
		return createSendMessage(update, ConstantMessages.ADDPRINTER_MAX_PLASTIC_MESSAGE.getMessage());
	}

	boolean parseAvailability(String value) {
		return switch (value.toLowerCase()) {
			case "true" -> true;
			case "false" -> false;
			default -> throw new IllegalArgumentException("Undefined availability: " + value);
		};
	}
}
