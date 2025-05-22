package org.printerbot.printerqueuetelegrambot.bot.command.generalCommands;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.config.WhiteList;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class StartCommand implements GeneralCommand {

	private final WhiteList whiteList;

	@Override
	public SendMessage apply(Update update) {
		String username = getChatUsername(update);
		StringBuilder builder = new StringBuilder(ConstantMessages.HELLO_MESSAGE.getMessage(username));
		builder.append(ConstantMessages.ALL_GENERAL_COMMANDS.getMessage());

		if (whiteList.isUserAdmin(username)) {
			builder.append("\n\n")
					.append(ConstantMessages.ALL_ADMINS_COMMANDS.getMessage());
		}

		builder.append("\n\n")
				.append(ConstantMessages.AUTHOR_INFO.getMessage())
				.append("\n\n")
				.append(ConstantMessages.GITHUB_INFO.getMessage());
		return createSendMessage(update, builder.toString());
	}

}
