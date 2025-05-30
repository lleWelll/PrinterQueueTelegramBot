package org.printerbot.printerqueuetelegrambot.bot.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.util.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class SkipPlasticCallback implements Callback{

	private final UserSessionManager sessionManager;

	private final SelectModelCallback selectModelCallback;

	@Override
	public SendMessage apply(String data, Update update) {
		Long chatId = getChatId(update);
		if (sessionManager.getChosenPlastic(chatId).isEmpty()) {
			return createSendMessage(update, "You didn't chose any plastic. Select at least one");
		}
		return selectModelCallback.apply("", update);
	}
}
