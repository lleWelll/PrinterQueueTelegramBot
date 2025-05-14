package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.user.UserSessionManager;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.service.QueueService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class DoneQueueCallback implements Callback {

	private final UserSessionManager sessionManager;

	private final QueueService queueService;

	@Override
	public SendMessage apply(String data, Update update) {
		String answer;
		Long chatId = getChatId(update);

		if (data.equals("Yes")) {
			QueueDto queueDto = sessionManager.getSession(chatId);

			try {
				queueService.joinQueue(getChatUsername(update), queueDto);
				answer = ConstantMessages.QUEUE_JOIN_CONFIRMATION.getFormattedMessage();
			} catch (Exception e) {
				answer = ConstantMessages.QUEUE_JOIN_ERROR.getFormattedMessage();
				log.error("Error occurred while saving queue: {}", e.getMessage());
			} finally {
				sessionManager.deleteSession(chatId);
			}

			return createSendMessage(update, answer);
		} else {
			log.info("Joining queue is canceled");
			answer = ConstantMessages.QUEUE_JOIN_CANCELED.getFormattedMessage();
			sessionManager.deleteSession(chatId);
			return createSendMessage(update, answer);
		}
	}
}
