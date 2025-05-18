package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PrinterDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class GetAllPrinterInfoCommand implements AdminCommand {

	private final PrinterDaoService printerDaoService;

	@Override
	public SendMessage apply(Update update) {
		StringBuilder builder = new StringBuilder("All Printers:\n\n\n");
		for(var pr : printerDaoService.getAll()) {
			builder.append(pr.getFullInfo()).append("\n\n");
		}
		return createSendMessage(update, builder.toString());
	}
}
