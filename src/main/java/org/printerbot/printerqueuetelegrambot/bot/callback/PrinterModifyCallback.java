package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class PrinterModifyCallback implements Callback {

	private final PrinterDaoService printerDaoService;

	private final PrinterSessionManager printerSessionManager;

	private final BotStateStorage botStateStorage;

	@Override
	public SendMessage apply(String data, Update update) {
		String[] dataParts = data.split(" ",2);
		String type = dataParts[0].trim();
		Long printerId = Long.valueOf(dataParts[1].trim());
		PrinterDto printer;
		try {
			printer = printerDaoService.getById(printerId);
		} catch (PrinterNotFoundException e) {
			log.error("Error occurred when getting PrinterDto: {}\n{}", printerId, e.getMessage());
			return createErrorMessage(update);
		}

		return switch (type) {
			case "Brand" -> {
				botStateStorage.setState(getChatId(update), BotState.WAITING_PRINTER_BRAND_MOD);
				printerSessionManager.addSession(getChatId(update), printer);
				yield createSendMessage(update, ConstantMessages.ADD_PRINTER_BRAND_MESSAGE.getMessage());
			}
			case "Model" -> {
				botStateStorage.setState(getChatId(update), BotState.WAITING_PRINTER_MODEL_MOD);
				printerSessionManager.addSession(getChatId(update), printer);
				yield createSendMessage(update, ConstantMessages.ADD_PRINTER_MODEL_MESSAGE.getMessage());
			}
			case "Features" -> {
				botStateStorage.setState(getChatId(update), BotState.WAITING_PRINTER_FEATURES_MOD);
				printerSessionManager.addSession(getChatId(update), printer);
				yield createSendMessage(update, ConstantMessages.ADD_PRINTER_FEATURES_MESSAGE.getMessage());
			}
			case "MaxPlasticCapacity" -> {
				botStateStorage.setState(getChatId(update), BotState.WAITING_PRINTER_MAX_PLASTIC_CAPACITY_MOD);
				printerSessionManager.addSession(getChatId(update), printer);
				yield createSendMessage(update, ConstantMessages.ADD_PRINTER_MAX_PLASTIC_MESSAGE.getMessage());
			}
			case "AddSupportedPlastic" -> {
				botStateStorage.setState(getChatId(update), BotState.WAITING_PRINTER_ADD_SUPPORTED_PLASTIC_MOD);
				printerSessionManager.addSession(getChatId(update), printer);
				yield createSendMessage(update, ConstantMessages.ADD_PRINTER_SUPPORTED_PLASTIC_MESSAGE.getMessage());
			}
			case "RemoveSupportedPlastic" -> {
				botStateStorage.setState(getChatId(update), BotState.WAITING_PRINTER_REMOVE_SUPPORTED_PLASTIC_MOD);
				printerSessionManager.addSession(getChatId(update), printer);
				yield createSendMessage(update, ConstantMessages.ADD_PRINTER_SUPPORTED_PLASTIC_MESSAGE.getMessage());
			}
			default -> createErrorMessage(update);
		};
	}
}
