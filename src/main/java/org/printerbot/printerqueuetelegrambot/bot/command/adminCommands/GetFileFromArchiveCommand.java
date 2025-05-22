package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.BotStateStorage;
import org.printerbot.printerqueuetelegrambot.bot.util.FileManager;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueArchiveDto;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.QueueArchiveDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetFileFromArchiveCommand implements AdminCommand {

	private final QueueArchiveDaoService queueArchiveDaoService;

	private final FileManager fileManager;

	private final BotStateStorage botStateStorage;

	@Override
	public SendMessage apply(Update update) {
		String[] parts = splitMessage(update, 2);
		Long queueId;

		try {
			queueId = Long.valueOf(parts[1]);
			QueueArchiveDto queueArchiveDto = queueArchiveDaoService.getById(queueId);
			fileManager.setLastAccessedFilePath(queueArchiveDto.getStlModelPath());
			botStateStorage.setState(getChatId(update), BotState.SENDING_DOCUMENT);
		} catch (Exception e) {
			log.error(e.getMessage());
			return createSyntaxErrorMessage(update, ConstantMessages.ARCHIVED_FILE_SYNTAX.getMessage());
		}

		return createSendMessage(update, ConstantMessages.GETTING_ARCHIVED_DOCUMENT.getMessage(queueId.toString()));
	}
}
