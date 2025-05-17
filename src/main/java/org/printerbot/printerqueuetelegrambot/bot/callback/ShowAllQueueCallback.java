package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.constants.Emoji;
import org.printerbot.printerqueuetelegrambot.model.dao.PrinterEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.service.QueueService;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PrinterDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShowAllQueueCallback implements Callback {

	private	final QueueService queueService;

	private final PrinterDaoService printerDaoService;

	@Override
	public SendMessage apply(String data, Update update) {
		Long printerId = Long.valueOf(data);

		PrinterEntity printer = printerDaoService.getEntityById(printerId);
		List<QueueDto> allQueueByPrinter = queueService.getAllQueueByPrinter(printer);

		StringBuilder builder = new StringBuilder();
		int counter = 1;
		for (var q : allQueueByPrinter) {
			builder.append(Emoji.getEmojiByNumber(counter)).append(" ").append(q.getQueueInfoWithUsername()).append("\n\n");
			counter++;
		}

		return createSendMessage(update, builder.toString());
	}
}
