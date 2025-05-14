package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.user.UserSessionManager;
import org.printerbot.printerqueuetelegrambot.bot.util.JsonHandler;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
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
		Long plasticId = Long.valueOf(data);
		PlasticDto plasticDto = plasticDaoService.getById(plasticId);
		PrinterDto printerDto = sessionManager.getChosenPrinter(getChatId(update));
		sessionManager.addSelectedPlastic(getChatId(update), List.of(plasticDto));

		String answer = ConstantMessages.CHOOSE_CONFIRMATION_MESSAGE.getFormattedMessage(plasticDto.getPlasticInfo()) +
				"\n\n" +
				ConstantMessages.CONFIRM_JOIN_MESSAGE.getFormattedMessage(printerDto.getPrinterInfo(), plasticDto.getPlasticInfo());

		SendMessage sendMessage = createSendMessage(update, answer);
		addKeyboard(sendMessage);

		return sendMessage;
	}

	private void addKeyboard(SendMessage message) {
		List<List<InlineKeyboardButton>> rows = new ArrayList<>();

		rows.add(List.of(createButton(CallbackType.DONE, "Yes")));
		rows.add(List.of(createButton(CallbackType.Cancel, "No")));

		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
		markup.setKeyboard(rows);

		message.setReplyMarkup(markup);
	}

	private InlineKeyboardButton createButton(CallbackType type, String text) {
		InlineKeyboardButton button = new InlineKeyboardButton();
		String jsonCallback = JsonHandler.listToJson(List.of(type.toString(), text));
		button.setText(text);
		button.setCallbackData(jsonCallback);
		return button;
	}
}
