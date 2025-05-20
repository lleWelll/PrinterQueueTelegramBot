package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
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

	@Override
	public SendMessage apply(String data, Update update) {
		long printerId = Long.parseLong(data);
		QueueDto first;
		try {
			queueService.next(printerId);
			first = queueService.getFirst(printerId);
		} catch (Exception e) {
			log.error(e.getMessage());
			return createErrorMessage(update);
		}
		String model = first.getStlModelPath() == null ? "Not uploaded" : first.getStlModelPath();
		return createSendMessage(update,
				ConstantMessages.NEXT_COMMAND_CONFIRMATION_MESSAGE.getMessage() +
						"\n\nUsername: " +
						first.getUsername() +
						"\nModel: " +
						model
		);
	}
}
