package org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.modifyPrinter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.ExpectedCommand;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.printerbot.printerqueuetelegrambot.bot.util.PrinterSessionManager;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
import org.printerbot.printerqueuetelegrambot.model.exceptions.PrinterNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PrinterDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class ModifyPrinterBrandExpectedCommand implements ExpectedCommand {

	private final PrinterSessionManager printerSessionManager;

	private final PrinterDaoService printerDaoService;

	private final BotStateStorage botStateStorage;

	@Override
	public SendMessage apply(Update update) {
		String brand = update.getMessage().getText().trim();
		PrinterDto printer = printerSessionManager.getSession(getChatId(update));
		try {
			printerDaoService.updateEntity(printer.getId(), (e) -> e.setBrand(brand));
		} catch (PrinterNotFoundException e) {
			log.error(e.getMessage());
			return createErrorMessage(update);
		}
		botStateStorage.setState(getChatId(update), BotState.NONE);
		printerSessionManager.deleteSession(getChatId(update));
		return createSendMessage(update, ConstantMessages.MODIFY_PRINTER_CONFIRMATION_MESSAGE.getMessage());
	}
}
