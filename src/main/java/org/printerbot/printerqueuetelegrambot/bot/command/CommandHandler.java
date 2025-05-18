package org.printerbot.printerqueuetelegrambot.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.adminCommands.*;
import org.printerbot.printerqueuetelegrambot.bot.command.generalCommands.*;
import org.printerbot.printerqueuetelegrambot.bot.config.WhiteList;
import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@Slf4j
public class CommandHandler {

	private final Map<String, GeneralCommand> generalCommands;

	private final Map<String, AdminCommand> adminCommands;

	private final GeneralCommand unknownCommand;

	private final NoPermissionCommand noPermissionCommand;

	private final WhiteList whiteList;

	public CommandHandler( WhiteList whiteList,
						   StartCommand startCommand,
						   InfoCommand infoCommand,
						   JoinCommand joinCommand,
						   LeaveCommand leaveCommand,
						   ShowAllQueueCommand showAllQueueCommand,
						   MyPositionCommand myPositionCommand,
						   GetAllPrinterInfoCommand getAllPrinterInfoCommand,
						   GetAllPlasticInfo getAllPlasticInfo,
						   SetAvailabilityCommand setAvailabilityCommand,
						   AddPrinterCommand addPrinterCommand,
						   RemovePrinterCommand removePrinterCommand,
						   UnknownCommand unknownCommand,
						   NoPermissionCommand noPermissionCommand) {
		this.generalCommands = Map.of(
				"/start", startCommand,
				"/help", startCommand,
				"/info", infoCommand,
				"/join", joinCommand,
				"/leave", leaveCommand,
				"/queue", showAllQueueCommand,
				"/myposition", myPositionCommand
		);
		this.adminCommands = Map.of(
				"/getprinters", getAllPrinterInfoCommand,
				"/getplastic", getAllPlasticInfo,
				"/setavailable", setAvailabilityCommand,
				"/addprinter", addPrinterCommand,
				"/removeprinter", removePrinterCommand
		);
		this.whiteList = whiteList;
		this.unknownCommand = unknownCommand;
		this.noPermissionCommand = noPermissionCommand;
	}

	public SendMessage handleCommand(Update update) {
		String commandText = update.getMessage().getText().split("\\s+", 2)[0];
		commandText = commandText.trim();
		Command command;
		if (adminCommands.containsKey(commandText)) {
			command = whiteList.isUserAdmin(update.getMessage().getChat().getUserName())
					? adminCommands.get(commandText)
					: noPermissionCommand;
		} else {
			command = generalCommands.getOrDefault(commandText, unknownCommand);
		}
		return command.apply(update);
	}


	public SendMessage handleUnknownCommand(Update update) {
		log.info("Detected unknown command: {} from user {}, chatId: {}",
				update.getMessage().getText(),
				update.getMessage().getChat().getUserName(),
				update.getMessage().getChatId());
		return unknownCommand.apply(update);
	}

}
