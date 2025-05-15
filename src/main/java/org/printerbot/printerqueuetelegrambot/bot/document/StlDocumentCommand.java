package org.printerbot.printerqueuetelegrambot.bot.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.callback.JoinConfirmationCallback;
import org.printerbot.printerqueuetelegrambot.bot.config.BotProperties;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.user.UserSessionManager;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class StlDocumentCommand implements DocumentCommand {

	private final BotProperties botProperties;

	private final UserSessionManager sessionManager;

	private final JoinConfirmationCallback joinConfirmationCallback;

	@Override
	public SendMessage apply(Update update, File file) {
		String fileUrl = file.getFileUrl(botProperties.getToken());

		QueueDto session = sessionManager.getSession(getChatId(update));
		if (session == null) {
			return createSendMessage(update, ConstantMessages.ERROR.getFormattedMessage());
		}
		sessionManager.addUploadedModelFile(getChatId(update), getFileName(update), fileUrl);

		return joinConfirmationCallback.apply(getChatId(update).toString(), update);
	}

}
