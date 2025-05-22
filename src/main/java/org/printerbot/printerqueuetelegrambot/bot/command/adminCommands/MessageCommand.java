package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.MessageBuffer;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.exceptions.QueueNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.QueueDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageCommand implements AdminCommand {

	private final QueueDaoService queueDaoService;

	private final MessageBuffer messageBuffer;

	@Override
	public SendMessage apply(Update update) {
		String[] parts = splitMessage(update, 3);

		try {
			Long id = Long.valueOf(parts[1]);
			QueueDto queueDto = queueDaoService.getById(id);

			messageBuffer.addMessage(new SendMessage(queueDto.getChatId().toString(), parts[2]));
			return createSendMessage(update, "Message send to @" + queueDto.getUsername());
		} catch (QueueNotFoundException | IllegalArgumentException e) {
			log.error(e.getMessage());
			return createSyntaxErrorMessage(update, ConstantMessages.MESSAGE_COMMAND_SYNTAX_MESSAGE.getMessage());
		}

	}
}
