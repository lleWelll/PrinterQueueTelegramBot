package org.printerbot.printerqueuetelegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.callback.CallbackHandler;
import org.printerbot.printerqueuetelegrambot.bot.command.CommandHandler;
import org.printerbot.printerqueuetelegrambot.bot.config.BotProperties;
import org.printerbot.printerqueuetelegrambot.bot.config.WhiteList;
import org.printerbot.printerqueuetelegrambot.bot.document.documuntCommads.DocumentHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

	private final BotProperties properties;
	private final CommandHandler commandHandler;
	private final CallbackHandler callbackHandler;
	private final DocumentHandler documentHandler;
	private final WhiteList whiteList;

	public TelegramBot(BotProperties properties,
					   CommandHandler commandHandler,
					   CallbackHandler callbackHandler,
					   DocumentHandler documentHandler,
					   WhiteList whiteList) {
		super(properties.getToken());
		this.properties = properties;
		this.commandHandler = commandHandler;
		this.callbackHandler = callbackHandler;
		this.documentHandler = documentHandler;
		this.whiteList = whiteList;
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.hasMessage()) {
			if (update.getMessage().hasDocument()) {
				sendMessage(documentHandler.handleDocument(update, getFile(update)));
			} else if (update.getMessage().hasText() && update.getMessage().getText().startsWith("/")) {
				sendMessage(commandHandler.handleCommand(update));
			} else {
				sendMessage(commandHandler.handleUnknownCommand(update));
			}
		}
		else if (update.hasCallbackQuery()) {
			sendMessage(callbackHandler.handleCallback(update));
		}
		else {
			sendMessage(commandHandler.handleUnknownCommand(update));
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

	private File getFile(Update update) {
		GetFile getFile = new GetFile(update.getMessage().getDocument().getFileId());
		try {
			return execute(getFile);
		} catch (TelegramApiException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
