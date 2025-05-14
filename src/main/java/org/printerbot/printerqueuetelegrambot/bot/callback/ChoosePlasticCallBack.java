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
		Long chatId = getChatId(update);
		Long plasticId = Long.valueOf(data);

		PlasticDto plastic = plasticDaoService.getById(plasticId);
		PrinterDto printer = sessionManager.getChosenPrinter(chatId);

		sessionManager.addSelectedPlastic(chatId, List.of(plastic));

		String confirmationMessage = ConstantMessages.CHOOSE_CONFIRMATION_MESSAGE.getFormattedMessage(plastic.getPlasticInfo()) +
				"\n\n" +
				ConstantMessages.CONFIRM_JOIN_MESSAGE.getFormattedMessage(printer.getPrinterInfo(), plastic.getPlasticInfo());

		SendMessage sendMessage = createSendMessage(update, confirmationMessage);
		addKeyboard(sendMessage);

		return sendMessage;
	}

	private void addKeyboard(SendMessage message) {
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(List.of(createButton(CallbackType.DONE, "Yes")));
		buttons.add(List.of(createButton(CallbackType.CANCEL, "No")));

		message.setReplyMarkup(new InlineKeyboardMarkup(buttons));
	}

	private InlineKeyboardButton createButton(CallbackType type, String text) {
		return InlineKeyboardButton.builder()
				.text(text)
				.callbackData(JsonHandler.listToJson(List.of(type.toString(), text)))
				.build();
	}
}
