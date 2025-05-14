package org.printerbot.printerqueuetelegrambot.bot.command.generalCommands;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.JsonHandler;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
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
public class JoinCommand implements GeneralCommand {

	private final QueueService queueService;

	private final PrinterDaoService  printerDaoService;

	@Override
	public SendMessage apply(Update update) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(getChatId(update));
		sendMessage.setText(ConstantMessages.SELECT_PRINTER_MESSAGE.getFormattedMessage());
		List<PrinterDto> printers = printerDaoService.getAll();
		addKeyboard(sendMessage, printers);
		return sendMessage;
	}

	private void addKeyboard(SendMessage message, List<PrinterDto> printers) {
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
		List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

		for (PrinterDto printer : printers) {
			InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
			inlineKeyboardButton.setText(printer.getBrand() + " " + printer.getModel());
			String jsonCallback = JsonHandler.listToJson(List.of(CallbackType.PRINTER_CHOOSE.toString(), printer.getId().toString()));
			inlineKeyboardButton.setCallbackData(jsonCallback);
			keyboardButtonsRow.add(inlineKeyboardButton);
		}

		List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
		rowList.add(keyboardButtonsRow);
		inlineKeyboardMarkup.setKeyboard(rowList);
		message.setReplyMarkup(inlineKeyboardMarkup);
	}
}
