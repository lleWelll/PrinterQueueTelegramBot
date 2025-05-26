package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.printerbot.printerqueuetelegrambot.bot.util.FileManager;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.service.QueueService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class NextCallback implements Callback {

	private final QueueService queueService;

	private final FileManager fileManager;

	private final BotStateStorage botStateStorage;

	@Override
	public SendMessage apply(String data, Update update) {
		long printerId = Long.parseLong(data);
		QueueDto first;
		try {
			first = queueService.getFirst(printerId);
			queueService.next(printerId);
			first = queueService.getFirst(printerId);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			return createSendMessage(update, ConstantMessages.QUEUE_IS_EMPTY_MESSAGE.getMessage());
		}
		catch (Exception e) {
			log.error(e.getMessage());
			return createErrorMessage(update);
		}
		String model = first.getStlModelPath() == null ? "Not uploaded" : first.getStlModelName();
		fileManager.setLastAccessedFilePath(first.getStlModelPath());
		botStateStorage.setState(getChatId(update), BotState.SENDING_DOCUMENT);
		return createSendMessage(update,
				ConstantMessages.NEXT_COMMAND_CONFIRMATION_MESSAGE.getMessage() +
						"\n\nUsername: " +
						first.getUsername() +
						"\nModel: " +
						model
		);
	}
}
