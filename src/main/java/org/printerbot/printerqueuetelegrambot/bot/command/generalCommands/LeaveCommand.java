package org.printerbot.printerqueuetelegrambot.bot.command.generalCommands;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.bot.util.JsonHandler;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.service.QueueService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LeaveCommand implements GeneralCommand {

	private final QueueService queueService;

	@Override
	public SendMessage apply(Update update) {
		List<QueueDto> queueEntries = queueService.getQueueListByUsername(getChatUsername(update));
		SendMessage sendMessage;
		if (queueEntries.isEmpty()) {
			return createSendMessage(update, ConstantMessages.NO_QUEUE_ENTRIES_MESSAGE.getFormattedMessage());
		} else if (queueEntries.size() == 1) {
			sendMessage = createSendMessage(update, ConstantMessages.LEAVE_COMMAND_MESSAGE.getFormattedMessage(queueEntries.size(), "entry") + "\n");
		} else {
			sendMessage = createSendMessage(update, ConstantMessages.LEAVE_COMMAND_MESSAGE.getFormattedMessage(queueEntries.size(), "entries") + "\n");
		}

		addQueueInfo(sendMessage, queueEntries);
		addKeyboard(sendMessage, queueEntries);
		return sendMessage;
	}

	private void addQueueInfo(SendMessage sendMessage, List<QueueDto> queueEntries) {
		StringBuilder sendMessageBuilder = new StringBuilder(sendMessage.getText());
		int counter = 1;
		for (var entry: queueEntries) {
			sendMessageBuilder.append("\n").append(counter).append(". ").append(entry.getQueueInfo());
			counter++;
		}
		sendMessage.setText(sendMessageBuilder.toString());
	}

	private void addKeyboard(SendMessage message, List<QueueDto> queueEntries) {
		List<List<InlineKeyboardButton>> rows = new ArrayList<>();

		for (var entry : queueEntries) {
			InlineKeyboardButton button = createButton(entry.getJoinedAt().toString(), CallbackType.QUEUE_ENTRY_CHOOSE, entry.getId().toString());
			rows.add(List.of(button));
		}
		rows.add(List.of(createButton("Cancel", CallbackType.CANCEL_LEAVE, "No")));
		message.setReplyMarkup(new InlineKeyboardMarkup(rows));
	}

	private InlineKeyboardButton createButton(String buttonName, CallbackType type, String data) {
		return InlineKeyboardButton.builder()
				.text(buttonName)
				.callbackData(JsonHandler.listToJson(List.of(type.toString(), data)))
				.build();
	}
}
