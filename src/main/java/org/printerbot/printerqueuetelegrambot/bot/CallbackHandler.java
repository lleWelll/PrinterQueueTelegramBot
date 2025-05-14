package org.printerbot.printerqueuetelegrambot.bot;

import org.printerbot.printerqueuetelegrambot.bot.callback.Callback;
import org.printerbot.printerqueuetelegrambot.bot.callback.ChoosePlasticCallBack;
import org.printerbot.printerqueuetelegrambot.bot.callback.ChoosePrinterCallBack;
import org.printerbot.printerqueuetelegrambot.bot.callback.DoneQueueCallback;
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
						   DoneQueueCallback doneQueueCallback) {
		this.callbacks = Map.of(
				CallbackType.PRINTER_CHOOSE, choosePrinterCallBack,
				CallbackType.PLASTIC_CHOOSE, choosePlasticCallBack,
				CallbackType.DONE, doneQueueCallback,
				CallbackType.CANCEL, doneQueueCallback
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
