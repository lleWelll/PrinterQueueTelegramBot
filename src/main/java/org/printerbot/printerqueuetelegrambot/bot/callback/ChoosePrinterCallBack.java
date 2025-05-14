package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.user.UserSessionManager;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PrinterDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class ChoosePrinterCallBack implements Callback {

	private final PrinterDaoService printerDaoService;

	private final UserSessionManager sessionManager;

	@Override
	public SendMessage apply(String data, Update update) {
		Long printerId = Long.valueOf(data);
		PrinterDto printer = printerDaoService.getById(printerId);
		sessionManager.addSelectedPrinter(getChatId(update), printer);

		String answer = ConstantMessages.PRINTER_CONFIRMATION_MESSAGE.getFormattedMessage(printer.getBrand(), printer.getModel()) +
				"\n\n" + ConstantMessages.ENTER_COMMAND_MESSAGE.getFormattedMessage("/plastic");

		return createSendMessage(update, answer);
	}
}
