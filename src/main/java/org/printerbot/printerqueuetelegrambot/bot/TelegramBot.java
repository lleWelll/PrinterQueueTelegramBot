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
import org.printerbot.printerqueuetelegrambot.bot.util.FileManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

	private final BotProperties properties;
	private final BotStateStorage botStateStorage;
	private final FileManager fileManager;
	private final CommandHandler commandHandler;
	private final CallbackHandler callbackHandler;
	private final DocumentHandler documentHandler;
	private final ExpectedCommandHandler expectedCommandHandler;

	public TelegramBot(BotProperties properties,
					   BotStateStorage botStateStorage,
					   FileManager fileManager,
					   CommandHandler commandHandler,
					   CallbackHandler callbackHandler,
					   DocumentHandler documentHandler,
					   ExpectedCommandHandler expectedCommandHandler) {
		super(properties.getToken());
		this.properties = properties;
		this.botStateStorage = botStateStorage;
		this.fileManager = fileManager;
		this.commandHandler = commandHandler;
		this.callbackHandler = callbackHandler;
		this.documentHandler = documentHandler;
		this.expectedCommandHandler = expectedCommandHandler;
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasCallbackQuery()) {
			handleCallback(update);
			return;
		}
		if (update.hasMessage()) {
			handleMessage(update);
			return;
		}
		sendMessage(commandHandler.handleUnknownCommand(update));
	}

	private void handleCallback(Update update) {
		CallbackQuery cq = update.getCallbackQuery();
		Long chatId = cq.getMessage().getChatId();

		sendMessage(callbackHandler.handleCallback(update));

		if (botStateStorage.getState(chatId) == BotState.SENDING_DOCUMENT) {
			String lastPath = fileManager.getLastAccessedFilePath();
			if (lastPath != null) {
				sendDocument(fileManager.getLastAccessedDocument(chatId.toString()));
			} else {
				log.info("Path to file is not found for user: {}", chatId);
			}
			botStateStorage.setState(chatId, BotState.NONE);
		}
	}

	private void handleMessage(Update update) {
		Message msg = update.getMessage();
		Long chatId = msg.getChatId();
		BotState state = botStateStorage.getState(chatId);

		if (state != BotState.NONE) {
			sendMessage(expectedCommandHandler.handleCommand(update, getBotState(update)));
			return;
		}

		if (msg.hasDocument()) {
			sendMessage(documentHandler.handleDocument(update, getFile(update)));
		}
		else if (msg.hasText() && msg.getText().startsWith("/")) {
			sendMessage(commandHandler.handleCommand(update));
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
			log.info("Sending message to chatId: '{}'\nMessage: \"{}\"", sendMessage.getChatId(), sendMessage.getText());
			execute(sendMessage);
		} catch (TelegramApiException e) {
			log.error(e.getMessage());
		}
	}

	private void sendDocument(SendDocument sendDocument) {
		try {
			log.info("Sending document to chatId: '{}'\nDocument: \"{}\"", sendDocument.getChatId(), sendDocument.getDocument());
			execute(sendDocument);
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
