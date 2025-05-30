package org.printerbot.printerqueuetelegrambot.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.command.adminCommands.*;
import org.printerbot.printerqueuetelegrambot.bot.command.generalCommands.*;
import org.printerbot.printerqueuetelegrambot.bot.config.WhiteList;
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

	public CommandHandler(WhiteList whiteList,
						  StartCommand startCommand,
						  InfoCommand infoCommand,
						  JoinCommand joinCommand,
						  LeaveCommand leaveCommand,
						  ShowAllQueueCommand showAllQueueCommand,
						  MyPositionCommand myPositionCommand,

						  GetAllPrinterInfoCommand getAllPrinterInfoCommand,
						  GetAllPlasticInfoCommand getAllPlasticInfoCommand,
						  GetArchiveCommand getAllArchiveCommand,
						  GetFileFromArchiveCommand getFileFromArchiveCommand,
						  FileCommand getfileCommand,
						  GetAllQueueCommand getAllQueueCommand,
						  AddPrinterCommand addPrinterCommand,
						  AddPlasticCommand addPlasticCommand,
						  RemovePrinterCommand removePrinterCommand,
						  RemovePlasticCommand removePlasticCommand,
						  SetAvailabilityCommand setAvailabilityCommand,
						  RemoveQueueCommand removeQueueCommand,
						  ModifyPrinterCommand modifyPrinterCommand,
						  NextCommand nextCommand,
						  MessageCommand messageCommand,
						  AddAdminCommand addAdminCommand,
						  RemoveAdminCommand removeAdminCommand,

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
		this.adminCommands = Map.ofEntries(
				Map.entry("/printers", getAllPrinterInfoCommand),
				Map.entry("/plastic", getAllPlasticInfoCommand),
				Map.entry("/archive", getAllArchiveCommand),
				Map.entry("/allqueue", getAllQueueCommand),
				Map.entry("/archivedfile", getFileFromArchiveCommand),
				Map.entry("/file", getfileCommand),

				Map.entry("/addprinter", addPrinterCommand),
				Map.entry("/addplastic", addPlasticCommand),
				Map.entry("/setavailable", setAvailabilityCommand),
				Map.entry("/removeprinter", removePrinterCommand),
				Map.entry("/removeplastic", removePlasticCommand),
				Map.entry("/modifyprinter", modifyPrinterCommand),

				Map.entry("/next", nextCommand),
				Map.entry("/remove", removeQueueCommand),
				Map.entry("/message", messageCommand),
				Map.entry("/addadmin", addAdminCommand),
				Map.entry("/removeadmin", removeAdminCommand)
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
