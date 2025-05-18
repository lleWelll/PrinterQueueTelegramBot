package org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.addPrinter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.ExpectedCommand;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.printerbot.printerqueuetelegrambot.bot.util.PrinterSessionManager;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.exceptions.PlasticNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PlasticDaoService;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PrinterDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddSupportedPlasticExpectedMessage implements ExpectedCommand {

	private final BotStateStorage botStateStorage;

	private final PrinterSessionManager printerSessionManager;

	private final PrinterDaoService printerDaoService;

	private final PlasticDaoService plasticDaoService;

	@Override
	public SendMessage apply(Update update) {
		Long chatId = getChatId(update);
		String supportedPlastic = update.getMessage().getText().trim();

		try {
			printerSessionManager.addSupportedPlastic(chatId, parseSupportedPlastic(supportedPlastic));
			printerDaoService.save(printerSessionManager.getSession(chatId));
			printerSessionManager.deleteSession(chatId);
		} catch (PlasticNotFoundException e) {
			log.error("Error occurred when getting plastic: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("Error occurred when parsing String to Long {}", e.getMessage());
			return createSyntaxErrorMessage(update,"1, 2, 3");
		} catch (Exception e) {
			log.error("Error occurred: {}", e.getMessage());
			return createSendMessage(update, ConstantMessages.ERROR.getMessage());
		}

		botStateStorage.setState(chatId, BotState.NONE);
		return createSendMessage(update, ConstantMessages.ADDPRINTER_CONFIRMATION_MESSAGE.getMessage());
	}

	private List<PlasticDto> parseSupportedPlastic(String value) {
		String[] plasticIds = value.split(",");
		return Arrays.stream(plasticIds)
				.map(String::trim)
				.map(Long::valueOf)
				.map(plasticDaoService::getById)
				.toList();
	}
}
