package org.printerbot.printerqueuetelegrambot.bot.command.generalCommands;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.service.QueueService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShowAllQueueCommand implements GeneralCommand {

	private final QueueService queueService;

	@Override
	public SendMessage apply(Update update) {
		List<QueueDto> allQueue = queueService.getAllQueue();

		StringBuilder builder = new StringBuilder();
		int counter = 1;
		for (var q : allQueue) {
			builder.append(counter).append(". ").append(q.getQueueInfoWithUsername()).append("\n");
			counter++;
		}

		return createSendMessage(update, builder.toString());
	}
}
