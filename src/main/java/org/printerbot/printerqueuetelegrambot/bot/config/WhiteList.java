package org.printerbot.printerqueuetelegrambot.bot.config;

import lombok.Data;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.model.exceptions.NotEnoughPermissionsException;
import org.springframework.beans.factory.annotation.Value;
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

	public boolean addNewAdmin(String username) {
		if (admins.contains(username)) return false;
		return admins.add(username);
	}

	public boolean removeAdmin(String username) throws NotEnoughPermissionsException {
		if (username.equals(getMainAdmin())) {
			throw new NotEnoughPermissionsException(ConstantMessages.NOT_ENOUGH_RIGHTS_MESSAGE.getMessage());
		}
		return admins.remove(username);
	}

	private String getMainAdmin() {
		return admins != null && !admins.isEmpty() ? admins.get(0) : null;
	}
}
