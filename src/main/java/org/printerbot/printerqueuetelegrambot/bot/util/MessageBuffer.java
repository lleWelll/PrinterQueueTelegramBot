package org.printerbot.printerqueuetelegrambot.bot.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayDeque;
import java.util.Queue;

@Component
public class MessageBuffer {

	private final Queue<SendMessage> messageBuffer = new ArrayDeque<>();

	public void addMessage(SendMessage message) {
		messageBuffer.add(message);
	}

	public SendMessage getMessage() {
		return messageBuffer.poll();
	}

	public int getSize() {
		return messageBuffer.size();
	}

}
