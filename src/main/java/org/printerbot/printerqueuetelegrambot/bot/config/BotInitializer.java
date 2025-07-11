package org.printerbot.printerqueuetelegrambot.bot.config;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.TelegramBot;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@RequiredArgsConstructor
public class BotInitializer {
	private final TelegramBot bot;

	@EventListener({ContextRefreshedEvent.class})
	public void init()throws TelegramApiException {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		try{
			telegramBotsApi.registerBot(bot);
		} catch (TelegramApiException e){

		}
	}
}
