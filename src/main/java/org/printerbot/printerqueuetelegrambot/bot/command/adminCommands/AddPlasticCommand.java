package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AddPlasticCommand implements AdminCommand {

	private final BotStateStorage botStateStorage;

	@Override
	public SendMessage apply(Update update) {
		Long chatId = getChatId(update);
		botStateStorage.setState(chatId, BotState.WAITING_PLASTIC_BRAND);
		return createSendMessage(update, ConstantMessages.ADD_PLASTIC_BRAND_MESSAGE.getMessage());
	}
}
