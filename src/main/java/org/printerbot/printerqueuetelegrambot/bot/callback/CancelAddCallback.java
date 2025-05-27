package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CancelAddCallback implements Callback {

	private final BotStateStorage botStateStorage;

	@Override
	public SendMessage apply(String data, Update update) {
		Long chatId = getChatId(update);
		botStateStorage.setState(chatId, BotState.NONE);
		return createSendMessage(update, ConstantMessages.ADDING_CANCELED.getMessage());
	}
}
