package org.printerbot.printerqueuetelegrambot.bot.util;

import org.printerbot.printerqueuetelegrambot.bot.constants.BotState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BotStateStorage {

	private final Map<Long, BotState> userStates = new HashMap();

	public void setState(long chatId, BotState state) {
		userStates.put(chatId, state);
	}

	public BotState getState(long chatId) {
		return userStates.getOrDefault(chatId, BotState.NONE);
	}

	public void clearState(long chatId) {
		userStates.remove(chatId);
	}

}
