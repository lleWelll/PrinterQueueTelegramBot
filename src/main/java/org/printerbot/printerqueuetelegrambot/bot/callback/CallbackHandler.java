package org.printerbot.printerqueuetelegrambot.bot.callback;

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
						   DoneLeaveCallback doneLeaveCallback,
						   ShowAllQueueCallback showAllQueueCallback,
						   MyPositionCallback myPositionCallback,
						   JoinConfirmationCallback joinConfirmationCallback,
						   PrinterModifyCallback modifyCallback,
						   NextCallback nextCallback) {
		this.callbacks = Map.ofEntries(
				Map.entry(CallbackType.PRINTER_CHOOSE, choosePrinterCallBack),
				Map.entry(CallbackType.PLASTIC_CHOOSE, choosePlasticCallBack),
				Map.entry(CallbackType.DONE_JOIN, doneJoinCallback),
				Map.entry(CallbackType.CANCEL_JOIN, doneJoinCallback),
				Map.entry(CallbackType.SKIP, joinConfirmationCallback),
				Map.entry(CallbackType.QUEUE_ENTRY_CHOOSE, chooseQueueEntryCallback),
				Map.entry(CallbackType.DONE_LEAVE, doneLeaveCallback),
				Map.entry(CallbackType.CANCEL_LEAVE, doneLeaveCallback),
				Map.entry(CallbackType.PRINTER_CHOOSE_SHOW, showAllQueueCallback),
				Map.entry(CallbackType.PRINTER_CHOOSE_POSITION, myPositionCallback),
				Map.entry(CallbackType.PRINTER_MODIFY, modifyCallback),
				Map.entry(CallbackType.PRINTER_NEXT_CHOOSE, nextCallback)
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
