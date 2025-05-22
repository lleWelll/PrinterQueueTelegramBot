package org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.addPrinter;

import lombok.RequiredArgsConstructor;
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
public class PrinterBrandExpectedCommand implements ExpectedCommand {

	private final BotStateStorage botStateStorage;

	private final PrinterSessionManager printerSessionManager;

	@Override
	public SendMessage apply(Update update) {
		Long chatId = getChatId(update);
		String brand = update.getMessage().getText().trim();
		printerSessionManager.createNewSession(chatId);
		printerSessionManager.addBrand(chatId, brand);
		botStateStorage.setState(chatId, BotState.WAITING_PRINTER_MODEL);
		return createSendMessage(update, ConstantMessages.ADD_PRINTER_MODEL_MESSAGE.getMessage());
	}
}
