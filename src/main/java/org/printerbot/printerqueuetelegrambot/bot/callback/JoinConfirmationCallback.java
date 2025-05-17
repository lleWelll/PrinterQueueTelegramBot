package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.UserSessionManager;
import org.printerbot.printerqueuetelegrambot.bot.util.JsonHandler;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JoinConfirmationCallback implements Callback{

	private final UserSessionManager sessionManager;

	@Override
	public SendMessage apply(String data, Update update) {
		Long chatId;
		if (data.equals("Skip")) {
			chatId = getChatId(update);
		} else {
			chatId = Long.valueOf(data);
		}

		QueueDto session = sessionManager.getSession(chatId);
		if (session == null) {
			return new SendMessage(chatId.toString(), ConstantMessages.ERROR.getMessage());
		}

		String confirmationMessage = ConstantMessages.CONFIRM_JOIN_MESSAGE.getMessage(
				session.getPrinter().getPrinterInfo(),
				session.getPlastics().get(0).getPlasticInfo(),
				session.getStlModelName() == null ? "Not uploaded" : session.getStlModelName()
		);

		SendMessage sendMessage = new SendMessage(chatId.toString(), confirmationMessage);
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
