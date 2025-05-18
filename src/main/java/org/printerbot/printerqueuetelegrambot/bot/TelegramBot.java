package org.printerbot.printerqueuetelegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.callback.CallbackHandler;
import org.printerbot.printerqueuetelegrambot.bot.command.CommandHandler;
import org.printerbot.printerqueuetelegrambot.bot.command.ExpectedCommandHandler;
import org.printerbot.printerqueuetelegrambot.bot.config.BotProperties;
import org.printerbot.printerqueuetelegrambot.bot.config.WhiteList;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.document.documuntCommads.DocumentHandler;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
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
	private final BotStateStorage botStateStorage;
	private final CommandHandler commandHandler;
	private final CallbackHandler callbackHandler;
	private final DocumentHandler documentHandler;
	private final ExpectedCommandHandler expectedCommandHandler;

	public TelegramBot(BotProperties properties,
					   BotStateStorage botStateStorage,
					   CommandHandler commandHandler,
					   CallbackHandler callbackHandler,
					   DocumentHandler documentHandler,
					   ExpectedCommandHandler expectedCommandHandler) {
		super(properties.getToken());
		this.properties = properties;
		this.botStateStorage = botStateStorage;
		this.commandHandler = commandHandler;
		this.callbackHandler = callbackHandler;
		this.documentHandler = documentHandler;
		this.expectedCommandHandler = expectedCommandHandler;
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.hasMessage()) {
			if (botStateStorage.getState(update.getMessage().getChatId()) != BotState.NONE) {
				sendMessage(expectedCommandHandler.handleCommand(update, getBotState(update)));
			} else if (update.getMessage().hasDocument()) {
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

	private BotState getBotState(Update update) {
		return botStateStorage.getState(update.getMessage().getChatId());
	}
}
