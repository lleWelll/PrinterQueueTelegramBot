package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.user.UserSessionManager;
import org.printerbot.printerqueuetelegrambot.bot.util.JsonHandler;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PlasticDaoService;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PrinterDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChoosePrinterCallBack implements Callback {

	private final PrinterDaoService printerDaoService;

	private final PlasticDaoService plasticDaoService;

	private final UserSessionManager sessionManager;

	@Override
	public SendMessage apply(String data, Update update) {
		Long printerId = Long.valueOf(data);
		PrinterDto printer = printerDaoService.getById(printerId);
		sessionManager.addSelectedPrinter(getChatId(update), printer);

		List<PlasticDto> plasticList = plasticDaoService.getAllAvailablePlastic();
		PrinterDto chosenPrinter = sessionManager.getChosenPrinter(getChatId(update));
		List<PlasticDto> filteredPlasticList = plasticList.stream().filter(chosenPrinter.getSupported_plastic()::contains).toList();

		String answer = ConstantMessages.CHOOSE_CONFIRMATION_MESSAGE.getFormattedMessage(printer.getPrinterInfo()) +
				"\n\n" +
				ConstantMessages.SELECT_PLASTIC_MESSAGE.getFormattedMessage();

		SendMessage sendMessage = createSendMessage(update, answer);
		addKeyboard(sendMessage, filteredPlasticList);
		return sendMessage;
	}

	private void addKeyboard(SendMessage sendMessage, List<PlasticDto> plasticList) {
		List<List<InlineKeyboardButton>> rows = new ArrayList<>();

		for (PlasticDto plastic : plasticList) {
			InlineKeyboardButton button = new InlineKeyboardButton();

			String jsonCallback = JsonHandler.listToJson(List.of(CallbackType.PLASTIC_CHOOSE.toString(), plastic.getId().toString()));
			button.setText(plastic.getBrand() + " " + plastic.getColor() + " " + plastic.getType());
			button.setCallbackData(jsonCallback);

			rows.add(List.of(button));
		}

		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
		markup.setKeyboard(rows);

		sendMessage.setReplyMarkup(markup);
	}
}
