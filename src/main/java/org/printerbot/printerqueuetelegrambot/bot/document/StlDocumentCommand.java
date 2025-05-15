package org.printerbot.printerqueuetelegrambot.bot.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.printerbot.printerqueuetelegrambot.bot.config.BotProperties;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.user.UserSessionManager;
import org.printerbot.printerqueuetelegrambot.bot.util.JsonHandler;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StlDocumentCommand implements DocumentCommand {

	private final BotProperties botProperties;

	private final UserSessionManager sessionManager;

	@Override
	public SendMessage apply(Update update, File file) {
		String fileUrl = file.getFileUrl(botProperties.getToken());

		QueueDto queueDto = sessionManager.getSession(getChatId(update));
		if (queueDto == null) {
			return createSendMessage(update, ConstantMessages.ERROR.getFormattedMessage());
		}
		queueDto.setStlModelPath(fileUrl);
		queueDto.setStlModelName(getFileName(update));

		PrinterDto printer = sessionManager.getChosenPrinter(getChatId(update));
		List<PlasticDto> plastic = sessionManager.getChosenPlastic(getChatId(update));

		String confirmationMessage = ConstantMessages.CONFIRM_JOIN_MESSAGE.getFormattedMessage(
						printer.getPrinterInfo(),
						plastic.get(0).getPlasticInfo(),
						queueDto.getStlModelName() == null ? "Not uploaded" : queueDto.getStlModelName()
						);

		SendMessage sendMessage = createSendMessage(update, confirmationMessage);
		addKeyboard(sendMessage);
		return sendMessage;
	}
	private void addKeyboard(SendMessage message) {
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(List.of(createButton(CallbackType.DONE_JOIN, "Yes")));
		buttons.add(List.of(createButton(CallbackType.CANCEL_JOIN, "No")));

		message.setReplyMarkup(new InlineKeyboardMarkup(buttons));
	}

	private InlineKeyboardButton createButton(CallbackType type, String text) {
		return InlineKeyboardButton.builder()
				.text(text)
				.callbackData(JsonHandler.listToJson(List.of(type.toString(), text)))
				.build();
	}
}
