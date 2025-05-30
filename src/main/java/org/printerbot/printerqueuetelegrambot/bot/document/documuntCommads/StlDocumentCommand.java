package org.printerbot.printerqueuetelegrambot.bot.document.documuntCommads;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.callback.JoinConfirmationCallback;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.document.DocumentCommand;
import org.printerbot.printerqueuetelegrambot.bot.util.FileManager;
import org.printerbot.printerqueuetelegrambot.bot.util.UserSessionManager;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class StlDocumentCommand implements DocumentCommand {

	private final FileManager fileManager;

	private final UserSessionManager sessionManager;

	private final JoinConfirmationCallback joinConfirmationCallback;

	@Override
	public SendMessage apply(Update update, File file) {
		String filePath = fileManager.downloadFile(file, getFileName(update), ".stl");

		QueueDto session = sessionManager.getQueueSession(getChatId(update));
		if (session == null) {
			return createSendMessage(update, ConstantMessages.ERROR.getMessage());
		}
		sessionManager.addUploadedModelFile(getChatId(update), getFileName(update), filePath);

		return joinConfirmationCallback.apply(getChatId(update).toString(), update);
	}
}
