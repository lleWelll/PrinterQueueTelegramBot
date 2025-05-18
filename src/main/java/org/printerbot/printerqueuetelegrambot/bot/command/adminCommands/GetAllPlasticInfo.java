package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PlasticDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class GetAllPlasticInfo implements AdminCommand{

	private final PlasticDaoService plasticDaoService;

	@Override
	public SendMessage apply(Update update) {
		StringBuilder builder = new StringBuilder("All Plastics:\n\n\n");
		for(var pl : plasticDaoService.getAll()) {
			builder.append(pl.getPlasticFullInfo()).append("\n\n");
		}
		return createSendMessage(update, builder.toString());
	}
}
