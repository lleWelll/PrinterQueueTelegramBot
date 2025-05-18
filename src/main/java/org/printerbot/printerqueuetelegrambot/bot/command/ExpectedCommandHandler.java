package org.printerbot.printerqueuetelegrambot.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.addPlastic.*;
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
								  SupportedPlasticExpectedMessage supportedPlasticExpectedMessage,
								  PlasticBrandExpectedCommand plasticBrandExpectedCommand,
								  PlasticTypeExpectedCommand plasticTypeExpectedCommand,
								  PlasticColorExpectedCommand plasticColorExpectedCommand,
								  PlasticDescriptionExpectedCommand plasticDescriptionExpectedCommand,
								  PlasticAvailabilityExpectedCommand plasticAvailabilityExpectedCommand) {
		this.expectedMessageCommands = Map.ofEntries(
				Map.entry(BotState.WAITING_PRINTER_BRAND, addPrinterBrandExpectedCommand),
				Map.entry(BotState.WAITING_PRINTER_MODEL, printerModelExpectedCommand),
				Map.entry(BotState.WAITING_PRINTER_FEATURES, printerFeaturesExpectedCommand),
				Map.entry(BotState.WAITING_PRINTER_AVAILABILITY, printerAvailabilityExpectedMessage),
				Map.entry(BotState.WAITING_PRINTER_MAX_PLASTIC_CAPACITY, maxPlasticExpectedCommand),
				Map.entry(BotState.WAITING_PRINTER_SUPPORTED_PLASTIC, supportedPlasticExpectedMessage),
				Map.entry(BotState.WAITING_PLASTIC_BRAND, plasticBrandExpectedCommand),
				Map.entry(BotState.WAITING_PLASTIC_TYPE, plasticTypeExpectedCommand),
				Map.entry(BotState.WAITING_PLASTIC_COLOR, plasticColorExpectedCommand),
				Map.entry(BotState.WAITING_PLASTIC_AVAILABILITY, plasticAvailabilityExpectedCommand),
				Map.entry(BotState.WAITING_PLASTIC_DESCRIPTION, plasticDescriptionExpectedCommand)
		);
	}

	public SendMessage handleCommand(Update update, BotState botState) {
		Command command = expectedMessageCommands.get(botState);
		return command.apply(update);
	}
}
