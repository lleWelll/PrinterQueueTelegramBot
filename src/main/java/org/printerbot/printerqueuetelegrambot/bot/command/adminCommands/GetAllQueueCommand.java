package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.QueueDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class GetAllQueueCommand implements AdminCommand {

	private final QueueDaoService queueDaoService;

	@Override
	public SendMessage apply(Update update) {
		StringBuilder builder = new StringBuilder("Queue:\n");
		AtomicInteger counter = new AtomicInteger(1);

		queueDaoService.getAll()
				.forEach(q -> {
					builder.append("\n\n")
							.append(counter.get())
							.append(". ")
							.append(q.getFullQueueInfo());
					counter.getAndIncrement();
				});

		return createSendMessage(update, builder.toString());
	}
}
