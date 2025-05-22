package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.QueueArchiveDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetArchiveCommand implements AdminCommand {

	private final QueueArchiveDaoService queueArchiveDaoService;

	@Override
	public SendMessage apply(Update update) {
		StringBuilder builder = new StringBuilder("Queue Archive:\n");
		AtomicInteger counter = new AtomicInteger(1);

		queueArchiveDaoService.getAll()
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
