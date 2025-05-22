package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.model.exceptions.PlasticNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.exceptions.PrinterNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PlasticDaoService;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PrinterDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetAvailabilityCommand implements AdminCommand {

	private final PrinterDaoService printerDaoService;

	private final PlasticDaoService plasticDaoService;

	@Override
	public SendMessage apply(Update update) {
		String[] parts = splitMessage(update, 4);
		String type;
		long id;
		boolean availability;

		try {
			type = parts[1];
			id = Long.parseLong(parts[2]);
			availability = parseAvailability(parts[3]);
			updateEntity(type, id, availability);
		} catch (PrinterNotFoundException | PlasticNotFoundException | IllegalArgumentException | IndexOutOfBoundsException  e) {
			log.error(e.getMessage());
			return createSyntaxErrorMessage(update, ConstantMessages.SET_AVAILABILITY_SYNTAX.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			return createSendMessage(update, ConstantMessages.ERROR.getMessage());
		}

		return createSendMessage(update, ConstantMessages.NEW_AVAILABILITY_SET_SUCCESSFULLY.getMessage());
	}

	private void updateEntity(String type, long id, boolean availability) {
		switch (type) {
			case "printer" -> printerDaoService.updateEntity(id, e -> e.setAvailable(availability));
			case "plastic" -> plasticDaoService.updateEntity(id, e -> e.setAvailable(availability));
			default -> throw new IllegalArgumentException("Undefined type: " + type);
		}
	}
}
