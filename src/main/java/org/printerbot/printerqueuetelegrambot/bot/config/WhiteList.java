package org.printerbot.printerqueuetelegrambot.bot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "whitelist")
@Data
public class WhiteList {
	private List<String> admins;

	public boolean isUserAdmin(String username) {
		return admins.contains(username);
	}
}
