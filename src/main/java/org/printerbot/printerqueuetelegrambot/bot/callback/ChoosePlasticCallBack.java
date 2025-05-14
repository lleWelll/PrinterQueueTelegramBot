package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.user.UserSessionManager;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PlasticDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChoosePlasticCallBack implements Callback {

	private final PlasticDaoService plasticDaoService;

	private final UserSessionManager sessionManager;

	@Override
	public SendMessage apply(String data, Update update) {
		Long plasticId = Long.valueOf(data);
		PlasticDto plasticDto = plasticDaoService.getById(plasticId);
		sessionManager.addSelectedPlastic(getChatId(update), List.of(plasticDto));

		String answer = ConstantMessages.PLASTIC_CONFIRMATION_MESSAGE.getFormattedMessage(
				plasticDto.getBrand(),
				plasticDto.getType(),
				plasticDto.getColor()
		) + "\n\n" +
				ConstantMessages.ENTER_COMMAND_MESSAGE.getFormattedMessage("/confirmJoin");
		return createSendMessage(update, answer);
	}
}
