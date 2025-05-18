package org.printerbot.printerqueuetelegrambot.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.addPrinter.*;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@Slf4j
public class ExpectedCommandHandler {

	private final Map<BotState, Command> expectedMessageCommands;


	public ExpectedCommandHandler(PrinterBrandExpectedCommand addPrinterBrandExpectedCommand,
								  PrinterModelExpectedCommand printerModelExpectedCommand,
								  PrinterFeaturesExpectedCommand printerFeaturesExpectedCommand,
								  PrinterAvailabilityExpectedMessage printerAvailabilityExpectedMessage,
								  MaxPlasticExpectedCommand maxPlasticExpectedCommand,
								  SupportedPlasticExpectedMessage supportedPlasticExpectedMessage) {
		this.expectedMessageCommands = Map.of(
				BotState.WAITING_PRINTER_BRAND, addPrinterBrandExpectedCommand,
				BotState.WAITING_PRINTER_MODEL, printerModelExpectedCommand,
				BotState.WAITING_PRINTER_FEATURES, printerFeaturesExpectedCommand,
				BotState.WAITING_PRINTER_AVAILABILITY, printerAvailabilityExpectedMessage,
				BotState.WAITING_PRINTER_MAX_PLASTIC_CAPACITY, maxPlasticExpectedCommand,
				BotState.WAITING_PRINTER_SUPPORTED_PLASTIC, supportedPlasticExpectedMessage
		);
	}

	public SendMessage handleCommand(Update update, BotState botState) {
		Command command = expectedMessageCommands.get(botState);
		return command.apply(update);
	}
}
