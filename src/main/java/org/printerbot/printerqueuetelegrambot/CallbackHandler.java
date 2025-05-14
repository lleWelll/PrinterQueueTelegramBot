package org.printerbot.printerqueuetelegrambot;

import org.printerbot.printerqueuetelegrambot.bot.callback.Callback;
import org.printerbot.printerqueuetelegrambot.bot.callback.TestCallback;
import org.printerbot.printerqueuetelegrambot.bot.constants.CallbackType;
import org.printerbot.printerqueuetelegrambot.bot.util.JsonHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component
public class CallbackHandler {

	private final Map<CallbackType, Callback> callbacks;

	public CallbackHandler(TestCallback testCallback) {
		this.callbacks = Map.of(
				CallbackType.TEST, testCallback
		);
	}

	public SendMessage handleCallback(Update update) {
//		List<String> callbackRequest = JsonHandler.invertJsonToList(update.getCallbackQuery().getData());
//		if (callbackRequest.isEmpty()) {
//			throw new RuntimeException("NADO POMENYAT!!");
//		}
//		CallbackType type = CallbackType.valueOf(callbackRequest.get(0).toUpperCase());
//		String data = callbackRequest.get(1);
//		Callback callback = callbacks.get(type);
//		return callback.apply(data, update);
		return null;
	}


}
