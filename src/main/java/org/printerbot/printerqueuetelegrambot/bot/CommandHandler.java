package org.printerbot.printerqueuetelegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.Command;
import org.printerbot.printerqueuetelegrambot.bot.command.generalCommands.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@Slf4j
public class CommandHandler {

	private final Map<String, Command> commands;

	private final Command unknownCommand;

	public CommandHandler(StartCommand startCommand,
						  InfoCommand infoCommand,
						  JoinCommand joinCommand,
						  LeaveCommand leaveCommand,
						  ShowAllQueueCommand showAllQueueCommand,
						  MyPositionCommand myPositionCommand,
						  UnknownCommand unknownCommand) {
		this.commands = Map.of(
				"/start", startCommand,
				"/help", startCommand,
				"/info", infoCommand,
				"/join", joinCommand,
				"/leave", leaveCommand,
				"/queue", showAllQueueCommand,
				"/myposition", myPositionCommand
		);
		this.unknownCommand = unknownCommand;
	}

	public SendMessage handleCommand(Update update) {
		String messageText = update.getMessage().getText();
		messageText = messageText.trim();
		Command command;
		command = commands.getOrDefault(messageText, unknownCommand);
		return command.apply(update);
	}

	public SendMessage handleUnknownCommand(Update update) {
		log.info("Detected unknown command: {}", update);
		return unknownCommand.apply(update);
	}

}
