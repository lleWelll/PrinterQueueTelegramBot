package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
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
public class MyPositionCallback implements Callback {

	private final QueueService queueService;

	private final PrinterDaoService printerDaoService;

	@Override
	public SendMessage apply(String data, Update update) {
		Long printerId = Long.valueOf(data);

		PrinterEntity printer = printerDaoService.getEntityById(printerId);
		List<QueueDto> allQueueByPrinter = queueService.getAllQueueByPrinter(printer);

		StringBuilder builder = new StringBuilder(ConstantMessages.YOUR_POSITION_MESSAGE.getFormattedMessage() + "\n");

		for (var q : allQueueByPrinter) {
			if (q.getUsername().equals(getChatUsername(update))) {
				builder.append(queueService.getMyPosition(q.getId(), allQueueByPrinter)).append(" - ").append(q.getQueueInfoWithUsername()).append("\n");
			}
		}
		return createSendMessage(update, builder.toString());
	}
}
