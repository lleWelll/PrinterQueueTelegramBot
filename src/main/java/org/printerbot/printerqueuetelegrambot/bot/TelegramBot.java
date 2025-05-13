package org.printerbot.printerqueuetelegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.BotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

	private final BotProperties properties;
	private final CommandHandler commandHandler;

	public TelegramBot(BotProperties properties,
					   @Autowired CommandHandler commandHandler) {
		super(properties.getToken());
		this.properties = properties;
		this.commandHandler = commandHandler;
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			if (update.getMessage().getText().startsWith("/")) {
				sendMessage(commandHandler.handleCommand(update));
			} else {
				sendMessage(commandHandler.handleUnknownCommand(update));
			}
		}
	}

	@Override
	public String getBotUsername() {
		return properties.getName();
	}

	private void sendMessage(SendMessage sendMessage) {
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			log.error(e.getMessage());
		}
	}
}
