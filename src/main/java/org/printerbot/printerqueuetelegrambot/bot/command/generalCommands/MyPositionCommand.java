package org.printerbot.printerqueuetelegrambot.bot.command.generalCommands;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.command.Command;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.JsonHandler;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.service.QueueService;
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
public class MyPositionCommand implements Command {

	private final PrinterDaoService printerDaoService;

	@Override
	public SendMessage apply(Update update) {
		SendMessage sendMessage = createSendMessage(update, ConstantMessages.SELECT_PRINTER_MESSAGE.getFormattedMessage());
		List<PrinterDto> printers = printerDaoService.getAllAvailablePrinters();
		addKeyboard(sendMessage, printers);
		return sendMessage;
	}
	private void addKeyboard(SendMessage message, List<PrinterDto> printers) {
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
		List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

		for (var printer : printers) {
			InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
			inlineKeyboardButton.setText(printer.getPrinterInfo());
			String jsonCallback = JsonHandler.listToJson(List.of(CallbackType.PRINTER_CHOOSE_POSITION.toString(), printer.getId().toString()));
			inlineKeyboardButton.setCallbackData(jsonCallback);
			keyboardButtonsRow.add(inlineKeyboardButton);
		}

		List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
		rowList.add(keyboardButtonsRow);
		inlineKeyboardMarkup.setKeyboard(rowList);
		message.setReplyMarkup(inlineKeyboardMarkup);
	}
}
