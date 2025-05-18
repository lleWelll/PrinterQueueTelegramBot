package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.model.exceptions.PrinterNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PlasticDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class RemovePlasticCommand implements AdminCommand {

	private final PlasticDaoService plasticDaoService;

	@Override
	public SendMessage apply(Update update) {
		String[] parts = splitMessage(update, 2);

		try {
			Long printerId = Long.valueOf(parts[1]);
			plasticDaoService.removeById(printerId);
		} catch (PrinterNotFoundException | IllegalArgumentException | IndexOutOfBoundsException e) {
			log.error("Error occurred when removing printer");
			return createSyntaxErrorMessage(update, ConstantMessages.REMOVE_PLASTIC_COMMAND_SYNTAX_MESSAGE.getMessage());
		}
		return createSendMessage(update, ConstantMessages.REMOVE_PLASTIC_COMMAND_CONFIRMATION_MESSAGE.getMessage());
	}
}
