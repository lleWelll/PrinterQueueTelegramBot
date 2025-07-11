package org.printerbot.printerqueuetelegrambot.bot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bot")
@Data
public class BotProperties {
	private String name;
	private String token;
	private String stlPath;
}
