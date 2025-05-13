package org.printerbot.printerqueuetelegrambot.bot;

import org.printerbot.printerqueuetelegrambot.bot.command.Command;
import org.printerbot.printerqueuetelegrambot.bot.command.StartCommand;
import org.printerbot.printerqueuetelegrambot.bot.command.UnknownCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CommandHandler {

	private final Map<String, Command> commands;

	public CommandHandler(Map<String, Command> commands,
						  @Autowired StartCommand startCommand) {
		this.commands = Map.of(
				"/start", startCommand
		);
	}

	public SendMessage handleCommand(Update update) {
		String messageText = update.getMessage().getText();
		messageText = messageText.trim();
		Command command;
		if (commands.containsKey(messageText)) {
			command = commands.get(messageText);
		}
		else {
			command = new UnknownCommand();
		}
		return command.apply(update);
	}

	public SendMessage handleUnknownCommand(Update update) {
		Command command = new UnknownCommand();
		return command.apply(update);
	}

}
