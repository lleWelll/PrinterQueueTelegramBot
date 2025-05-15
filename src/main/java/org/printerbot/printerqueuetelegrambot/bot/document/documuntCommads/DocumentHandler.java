package org.printerbot.printerqueuetelegrambot.bot.document.documuntCommads;

import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.document.DocumentCommand;
import org.printerbot.printerqueuetelegrambot.bot.document.StlDocumentCommand;
import org.printerbot.printerqueuetelegrambot.model.exceptions.FileFormatException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;


@Component
@Slf4j
public class DocumentHandler {

	private final Map<String, DocumentCommand> commands;

	private final DocumentCommand unknownDocumentCommand;

	public DocumentHandler(StlDocumentCommand stlCommand,
						   UnknownDocumentCommand unknownDocumentCommand) {
		this.commands = Map.of(
				".stl", stlCommand
		);
		this.unknownDocumentCommand = unknownDocumentCommand;
	}

	public SendMessage handleDocument(Update update, File file) {
		log.info("Starting handling of document {}", update.getMessage().getDocument().getFileName());
		String fileName = update.getMessage().getDocument().getFileName();
		String fileFormat = extractFileFormat(fileName);
		DocumentCommand command;
		command = commands.getOrDefault(fileFormat, unknownDocumentCommand);
		return command.apply(update, file);
	}

	private String extractFileFormat(String fileName) {
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot < 0) {
			throw new FileFormatException("Can't extract format of " + fileName + " file");
		}
		return fileName.substring(lastDot);
	}
}
