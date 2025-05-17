package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.UserSessionManager;
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
		Long chatId = getChatId(update);
		Long printerId = Long.valueOf(data);

		PrinterDto printer = printerDaoService.getById(printerId);
		sessionManager.addSelectedPrinter(chatId, printer);

		List<PlasticDto> availablePlastics = plasticDaoService.getAllAvailablePlastic();
		List<PlasticDto> supportedPlastics = availablePlastics.stream()
				.filter(printer.getSupported_plastic()::contains)
				.toList();

		String text = ConstantMessages.CHOOSE_CONFIRMATION_MESSAGE.getMessage(printer.getPrinterInfo()) +
				"\n\n" +
				ConstantMessages.SELECT_PLASTIC_MESSAGE.getMessage();

		SendMessage sendMessage = createSendMessage(update, text);
		addKeyboard(sendMessage, supportedPlastics);

		return sendMessage;
	}

	private void addKeyboard(SendMessage message, List<PlasticDto> plasticList) {
		List<List<InlineKeyboardButton>> rows = new ArrayList<>();

		for (PlasticDto plastic : plasticList) {
			InlineKeyboardButton button = createButton(plastic.getPlasticInfo(), CallbackType.PLASTIC_CHOOSE, plastic.getId().toString());
			rows.add(List.of(button));
		}
		rows.add(List.of(createButton("Cancel", CallbackType.CANCEL_JOIN, "No")));
		message.setReplyMarkup(new InlineKeyboardMarkup(rows));
	}

	private InlineKeyboardButton createButton(String buttonName, CallbackType type, String data) {
		return InlineKeyboardButton.builder()
				.text(buttonName)
				.callbackData(JsonHandler.listToJson(List.of(type.toString(), data)))
				.build();
	}
}
