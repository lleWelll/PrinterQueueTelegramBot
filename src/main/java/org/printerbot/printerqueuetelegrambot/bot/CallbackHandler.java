package org.printerbot.printerqueuetelegrambot.bot;

import org.printerbot.printerqueuetelegrambot.bot.callback.*;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.util.JsonHandler;
import org.printerbot.printerqueuetelegrambot.model.exceptions.EmptyCallbackException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component
public class CallbackHandler {

	private final Map<CallbackType, Callback> callbacks;

	public CallbackHandler(ChoosePrinterCallBack choosePrinterCallBack,
						   ChoosePlasticCallBack choosePlasticCallBack,
						   DoneJoinCallback doneJoinCallback,
						   ChooseQueueEntryCallback chooseQueueEntryCallback,
						   DoneLeaveCallback doneLeaveCallback) {
		this.callbacks = Map.of(
				CallbackType.PRINTER_CHOOSE, choosePrinterCallBack,
				CallbackType.PLASTIC_CHOOSE, choosePlasticCallBack,
				CallbackType.DONE_JOIN, doneJoinCallback,
				CallbackType.CANCEL_JOIN, doneJoinCallback,
				CallbackType.QUEUE_ENTRY_CHOOSE, chooseQueueEntryCallback,
				CallbackType.DONE_LEAVE, doneLeaveCallback,
				CallbackType.CANCEL_LEAVE, doneJoinCallback
		);
	}

	public SendMessage handleCallback(Update update) {
		List<String> callbackRequest = JsonHandler.jsonToList(update.getCallbackQuery().getData());
		if (callbackRequest.isEmpty()) {
			throw new EmptyCallbackException("CallbackRequest is empty: " + callbackRequest);
		}
		CallbackType type = CallbackType.valueOf(callbackRequest.get(0).toUpperCase());
		String data = callbackRequest.get(1);
		Callback callback = callbacks.get(type);
		return callback.apply(data, update);
	}

}
