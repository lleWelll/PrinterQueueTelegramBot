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
public class ModifyPrinterCommand implements AdminCommand {

	private final PrinterDaoService printerDaoService;

	@Override
	public SendMessage apply(Update update) {
		String[] parts = splitMessage(update, 2);
		long printerId;
		SendMessage sendMessage;

		try {
			printerId = Long.parseLong(parts[1]);
			PrinterDto printer = printerDaoService.getById(printerId);
			sendMessage = createSendMessage(update,
					ConstantMessages.CHOOSE_CONFIRMATION_MESSAGE.getMessage(printer.getFullInfo()) +
					"\n\n" + ConstantMessages.CHOOSE_PROPERTY_MESSAGE.getMessage());
			addKeyboard(sendMessage, printerId);
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			log.info(e.getMessage());
			return createSyntaxErrorMessage(update, ConstantMessages.MODIFY_PRINTER_COMMAND_SYNTAX_MESSAGE.getMessage());
		}
		return sendMessage;
	}

	private void addKeyboard(SendMessage message, Long printerId) {
		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
		List<InlineKeyboardButton> row = new ArrayList<>();

		row.add(createButton("Brand", CallbackType.PRINTER_MODIFY, "Brand " + printerId));
		row.add(createButton("Model", CallbackType.PRINTER_MODIFY, "Model " + printerId));
		row.add(createButton("Features", CallbackType.PRINTER_MODIFY, "Features " + printerId));
		row.add(createButton("Maximum Plastic Capacity", CallbackType.PRINTER_MODIFY, "MaxPlasticCapacity " + printerId));
		row.add(createButton("Add Supported Plastic", CallbackType.PRINTER_MODIFY, "AddSupportedPlastic " + printerId));
		row.add(createButton("Remove Supported Plastic", CallbackType.PRINTER_MODIFY, "RemoveSupportedPlastic " + printerId));

		List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
		rowList.add(row);
		markup.setKeyboard(rowList);
		message.setReplyMarkup(markup);
	}
	private InlineKeyboardButton createButton(String buttonName, CallbackType type, String data) {
		return InlineKeyboardButton.builder()
				.text(buttonName)
				.callbackData(JsonHandler.listToJson(List.of(type.toString(), data)))
				.build();
	}
}

