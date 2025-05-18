package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddPrinterCommand implements AdminCommand {

	private final BotStateStorage botStateStorage;


	@Override
	public SendMessage apply(Update update) {
		Long chatId = getChatId(update);
		botStateStorage.setState(chatId, BotState.WAITING_PRINTER_BRAND);
		return createSendMessage(update, ConstantMessages.ADDPRINTER_BRAND_MESSAGE.getMessage());
	}

//	private Long[] parseSupportedPlastic(String stringArray) {
//		String[] elements = stringArray.split(",");
//		if (Arrays.stream(elements).allMatch(str -> str.matches("\\d+"))) {
//			throw new IllegalArgumentException("Cannot map String -> Long: " + Arrays.toString(elements));
//		}
//		return Arrays.stream(elements).map(Long::parseLong).toArray(Long[]::new);
//	}
}
