package org.printerbot.printerqueuetelegrambot.bot;

import lombok.Data;
import org.hibernate.annotations.Source;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bot")
@Data
public class BotProperties {
	private String name;
	private String token;
}
