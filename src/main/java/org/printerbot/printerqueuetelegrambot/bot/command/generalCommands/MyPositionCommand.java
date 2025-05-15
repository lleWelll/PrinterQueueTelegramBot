package org.printerbot.printerqueuetelegrambot.bot.command.generalCommands;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.command.Command;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.service.QueueService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyPositionCommand implements Command {

	private final QueueService queueService;

	@Override
	public SendMessage apply(Update update) {
		List<QueueDto> queueListByUsername = queueService.getQueueListByUsername(getChatUsername(update));

		if(queueListByUsername.isEmpty()) {
			return createSendMessage(update, ConstantMessages.NO_QUEUE_ENTRIES_MESSAGE.getFormattedMessage());
		}

		StringBuilder builder = new StringBuilder(ConstantMessages.YOUR_POSITION_MESSAGE.getFormattedMessage() + "\n");
		for (var q : queueListByUsername) {
			builder.append(queueService.getMyPosition(q.getId())).append(" - ").append(q.getQueueInfoWithUsername()).append("\n");
		}
		return createSendMessage(update, builder.toString());
	}
}
