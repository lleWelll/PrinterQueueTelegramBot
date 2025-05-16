package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.UserSessionManager;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.service.QueueService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class DoneLeaveCallback implements Callback {

	private final UserSessionManager sessionManager;

	private final QueueService queueService;

	@Override
	public SendMessage apply(String data, Update update) {
		String answer;
		Long chatId = getChatId(update);

		if (data.equals("Yes")) {
			QueueDto queueDto = sessionManager.getSession(chatId);

			try {
				queueService.leaveQueue(queueDto);
				answer = ConstantMessages.LEAVE_CONFIRMATION_MESSAGE.getFormattedMessage();
			} catch (Exception e) {
				answer = ConstantMessages.ERROR.getFormattedMessage();
				log.error("Error occurred while deleting queue: {}", e.getMessage());
			} finally {
				sessionManager.deleteSession(chatId);
			}

		} else {
			log.info("Leaving queue is canceled");
			answer = ConstantMessages.QUEUE_LEAVE_CANCELED.getFormattedMessage();
			sessionManager.deleteSession(chatId);
		}
		return createSendMessage(update, answer);
	}
}
