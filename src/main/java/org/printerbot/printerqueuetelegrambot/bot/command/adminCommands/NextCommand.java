package org.printerbot.printerqueuetelegrambot.bot.command.adminCommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.JsonHandler;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
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
@Slf4j
public class NextCommand implements AdminCommand {

	private final PrinterDaoService printerDaoService;

	@Override
	public SendMessage apply(Update update) {
		List<PrinterDto> allPrinters = printerDaoService.getAll();
		SendMessage sendMessage = createSendMessage(update, ConstantMessages.SELECT_PRINTER_MESSAGE.getMessage());
		addKeyboard(sendMessage, allPrinters);
		return sendMessage;
	}

	private void addKeyboard(SendMessage message, List<PrinterDto> printers) {
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
		List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

		for (var printer : printers) {
			keyboardButtonsRow.add(createButton(printer.getPrinterInfo(), CallbackType.PRINTER_NEXT_CHOOSE, printer.getId().toString()));
		}

		List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
		rowList.add(keyboardButtonsRow);
		inlineKeyboardMarkup.setKeyboard(rowList);
		message.setReplyMarkup(inlineKeyboardMarkup);
	}
	private InlineKeyboardButton createButton(String buttonName, CallbackType type, String data) {
		return InlineKeyboardButton.builder()
				.text(buttonName)
				.callbackData(JsonHandler.listToJson(List.of(type.toString(), data)))
				.build();
	}
}
