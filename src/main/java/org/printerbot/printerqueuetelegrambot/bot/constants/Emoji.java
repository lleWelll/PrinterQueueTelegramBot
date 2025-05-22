package org.printerbot.printerqueuetelegrambot.bot.constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Emoji {

	ZERO("0️⃣", 0),
	ONE("1️⃣", 1),
	TWO("2️⃣", 2),
	THREE("3️⃣", 3),
	FOUR("4️⃣", 4),
	FIVE("5️⃣", 5),
	SIX("6️⃣", 6),
	SEVEN("7️⃣", 7),
	EIGHT("8️⃣", 8),
	NINE("9️⃣", 9);

	private final String emoji;

	private final int numberEquivalent;

	Emoji(String emoji, int number) {
		this.emoji = emoji;
		this.numberEquivalent = number;
	}

	public String get() {
		return this.emoji;
	}

	public static String findByNumber(int number) {
		for (Emoji emoji : values()) {
			if (emoji.numberEquivalent == number) {
				return emoji.emoji;
			}
		}
		throw new IllegalArgumentException("Can't find emoji with number equivalent " + number);
	}

	public static String getEmojiByNumber(int number) {
		StringBuilder builder = new StringBuilder();
		String.valueOf(number)
				.chars()
				.map(c -> c - '0')
				.forEach(i -> {
					builder.append(Emoji.findByNumber(i));
					log.info(builder.toString());
				});
		return builder.toString();
	}
}
