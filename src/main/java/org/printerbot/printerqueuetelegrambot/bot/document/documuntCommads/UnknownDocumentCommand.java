package org.printerbot.printerqueuetelegrambot.bot.document.documuntCommads;

import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.document.DocumentCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnknownDocumentCommand implements DocumentCommand {
	@Override
	public SendMessage apply(Update update, File file) {
		return createSendMessage(update, ConstantMessages.UNKNOWN_DOCUMENT_MESSAGE.getFormattedMessage());
	}
}
