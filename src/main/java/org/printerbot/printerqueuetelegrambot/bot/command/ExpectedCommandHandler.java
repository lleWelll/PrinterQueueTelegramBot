package org.printerbot.printerqueuetelegrambot.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.addPrinter.*;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.model.exceptions.PlasticNotFoundException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@Slf4j
public class ExpectedCommandHandler {

	private final Map<BotState, Command> expectedMessageCommands;


	public ExpectedCommandHandler(AddPrinterBrandExpectedCommand addPrinterBrandExpectedCommand,
								  AddPrinterModelExpectedCommand addPrinterModelExpectedCommand,
								  AddPrinterFeaturesExpectedCommand addPrinterFeaturesExpectedCommand,
								  AddPrinterAvailabilityExpectedMessage addPrinterAvailabilityExpectedMessage,
								  AddPrinterMaxPlasticExpectedCommand addPrinterMaxPlasticExpectedCommand,
								  AddSupportedPlasticExpectedMessage addSupportedPlasticExpectedMessage) {
		this.expectedMessageCommands = Map.of(
				BotState.WAITING_PRINTER_BRAND, addPrinterBrandExpectedCommand,
				BotState.WAITING_PRINTER_MODEL, addPrinterModelExpectedCommand,
				BotState.WAITING_PRINTER_FEATURES, addPrinterFeaturesExpectedCommand,
				BotState.WAITING_PRINTER_AVAILABILITY, addPrinterAvailabilityExpectedMessage,
				BotState.WAITING_PRINTER_MAX_PLASTIC_CAPACITY, addPrinterMaxPlasticExpectedCommand,
				BotState.WAITING_PRINTER_SUPPORTED_PLASTIC, addSupportedPlasticExpectedMessage
		);
	}

	public SendMessage handleCommand(Update update, BotState botState) {
		Command command = expectedMessageCommands.get(botState);
		return command.apply(update);
	}
}
