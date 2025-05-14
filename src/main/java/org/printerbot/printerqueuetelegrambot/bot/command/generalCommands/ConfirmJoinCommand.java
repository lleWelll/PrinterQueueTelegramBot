package org.printerbot.printerqueuetelegrambot.bot.command.generalCommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.user.UserSessionManager;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.service.QueueService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConfirmJoinCommand implements GeneralCommand {

	private final UserSessionManager sessionManager;

	private final QueueService queueService;

	@Override
	public SendMessage apply(Update update) {
		String answer;
		Long chatId = getChatId(update);
		QueueDto queueDto = sessionManager.getSession(chatId);

		try{
			queueService.joinQueue(getChatUsername(update), queueDto);
			answer = ConstantMessages.QUEUE_JOIN_CONFIRMATION.getFormattedMessage();
		} catch (Exception e) {
			answer = ConstantMessages.QUEUE_JOIN_ERROR.getFormattedMessage();
			log.error("Error occurred while saving queue: {}", e.getMessage());
			e.printStackTrace();
		}

		return createSendMessage(update, answer);
	}
}
