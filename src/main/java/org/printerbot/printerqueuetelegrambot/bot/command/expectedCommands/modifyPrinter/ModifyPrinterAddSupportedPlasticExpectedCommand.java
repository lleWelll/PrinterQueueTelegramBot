package org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.modifyPrinter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.expectedCommands.ExpectedCommand;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.printerbot.printerqueuetelegrambot.bot.util.PrinterSessionManager;
import org.printerbot.printerqueuetelegrambot.model.dao.PlasticEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
import org.printerbot.printerqueuetelegrambot.model.mapper.PlasticMapper;
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
public class ModifyPrinterAddSupportedPlasticExpectedCommand implements ExpectedCommand {

	private final PrinterSessionManager printerSessionManager;

	private final PrinterDaoService printerDaoService;

	private final PlasticDaoService plasticDaoService;

	private final BotStateStorage botStateStorage;

	private final PlasticMapper mapper;

	@Override
	public SendMessage apply(Update update) {
		String text = update.getMessage().getText().trim();
		PrinterDto printer = printerSessionManager.getSession(getChatId(update));
		try {
			List<PlasticEntity> plasticEntityList = mapper.toPlasticEntityList(parseSupportedPlastic(text));
			printerDaoService.updateEntity(printer.getId(), (e) -> e.addPlastic(plasticEntityList));
		} catch (Exception e) {
			log.error(e.getMessage());
			return createErrorMessage(update);
		}
		botStateStorage.setState(getChatId(update), BotState.NONE);
		printerSessionManager.deleteSession(getChatId(update));
		return createSendMessage(update, ConstantMessages.MODIFY_PRINTER_CONFIRMATION_MESSAGE.getMessage());
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
