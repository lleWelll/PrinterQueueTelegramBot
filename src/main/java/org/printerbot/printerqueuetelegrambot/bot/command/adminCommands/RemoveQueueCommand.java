package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.model.exceptions.QueueNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.service.QueueService;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.QueueDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class RemoveQueueCommand implements AdminCommand {

	private final QueueService queueService;

	private final QueueDaoService queueDaoService;

	@Override
	public SendMessage apply(Update update) {
		String[] parts = splitMessage(update, 2);
		try {
			Long queueId = Long.valueOf(parts[1]);
			queueService.leaveQueue(queueDaoService.getById(queueId));
			return createSendMessage(update, ConstantMessages.REMOVING_CONFIRMATION_MESSAGE.getMessage());
		} catch (QueueNotFoundException | IndexOutOfBoundsException e) {
			log.error(e.getMessage());
			return createSyntaxErrorMessage(update, ConstantMessages.REMOVE_QUEUE_SYNTAX_MESSAGE.getMessage());
		} catch (Exception e) {
			log.error("Error occurred while deleting queue: {}", e.getMessage());
			return createErrorMessage(update);
		}
	}
}
