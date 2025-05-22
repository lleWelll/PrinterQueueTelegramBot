package org.printerbot.printerqueuetelegrambot.bot.constants;

import lombok.Getter;

@Getter
public enum Constants {

	NOT_UPLOADED_MODEL("Not Uploaded");

	private final String value;

	Constants(String value) {
		this.value = value;
	}
}
