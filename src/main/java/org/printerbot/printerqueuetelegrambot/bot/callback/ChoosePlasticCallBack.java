package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.UserSessionManager;
import org.printerbot.printerqueuetelegrambot.bot.util.JsonHandler;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PlasticDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChoosePlasticCallBack implements Callback {

	private final PlasticDaoService plasticDaoService;

	private final UserSessionManager sessionManager;

	@Override
	public SendMessage apply(String data, Update update) {
		Long chatId = getChatId(update);
		Long plasticId = Long.valueOf(data);

		PlasticDto plastic = plasticDaoService.getById(plasticId);
		sessionManager.addSelectedPlastic(chatId, List.of(plastic));

		String answer = ConstantMessages.CHOOSE_CONFIRMATION_MESSAGE.getMessage(plastic.getPlasticInfo()) +
				"\n\n" +
				ConstantMessages.UPLOAD_STL_FILE_MESSAGE.getMessage();
		SendMessage sendMessage = createSendMessage(update, answer);
		addKeyboard(sendMessage);
		return sendMessage;
	}

	private void addKeyboard(SendMessage message) {
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(List.of(createButton(CallbackType.SKIP, "Skip")));
		buttons.add(List.of(createButton(CallbackType.CANCEL_JOIN, "Cancel")));

		message.setReplyMarkup(new InlineKeyboardMarkup(buttons));
	}

	private InlineKeyboardButton createButton(CallbackType type, String text) {
		return InlineKeyboardButton.builder()
				.text(text)
				.callbackData(JsonHandler.listToJson(List.of(type.toString(), text)))
				.build();
	}
}
