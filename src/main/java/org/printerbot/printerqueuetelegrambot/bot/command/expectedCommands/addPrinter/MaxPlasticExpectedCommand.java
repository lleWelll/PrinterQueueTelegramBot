package org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.addPrinter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.ExpectedCommand;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.printerbot.printerqueuetelegrambot.bot.util.PrinterSessionManager;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PlasticDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class MaxPlasticExpectedCommand implements ExpectedCommand {

	private final BotStateStorage botStateStorage;

	private final PrinterSessionManager printerSessionManager;

	private final PlasticDaoService plasticDaoService;

	@Override
	public SendMessage apply(Update update) {
		String max = update.getMessage().getText().trim();

		try {
			printerSessionManager.addMaximumPlastic(getChatId(update), parseMaxPlastic(max));
		} catch (IllegalArgumentException e) {
			log.error("Error Occurred when parsing String -> int: {}", e.getMessage());
			return createSyntaxErrorMessage(update, "Positive numbers");
		}

		String answer = getAllPlasticInfo() +
				"\n" +
				ConstantMessages.ADDPRINTER_SUPPORTED_PLASTIC_MESSAGE.getMessage();

		botStateStorage.setState(getChatId(update), BotState.WAITING_PRINTER_SUPPORTED_PLASTIC);
		return createSendMessage(update, answer);
	}

	private int parseMaxPlastic(String maxPlastic) {
		if (maxPlastic.matches("\\d+")) {
			return Integer.parseInt(maxPlastic);
		} else {
			throw new IllegalArgumentException("Undefined maxPlastic: " + maxPlastic);
		}
	}

	private String getAllPlasticInfo() {
		StringBuilder builder = new StringBuilder("All Plastic:\n");
		for (var pl : plasticDaoService.getAll()) {
			builder.append(pl.getPlasticFullInfo()).append("\n");
		}
		return builder.toString();
	}
}
