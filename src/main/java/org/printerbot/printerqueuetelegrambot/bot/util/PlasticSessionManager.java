package org.printerbot.printerqueuetelegrambot.bot.util;

import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.enums.PlasticColor;
import org.printerbot.printerqueuetelegrambot.model.enums.PlasticType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class PlasticSessionManager {

	private final Map<Long, PlasticDto> plasticSession = new HashMap<>();

	public PlasticDto getSession(Long chatId) {
		PlasticDto plastic = plasticSession.get(chatId);
		if (plastic == null) {
			log.warn("No plastic session for user {}", chatId);
			return null;
		}
		log.info("Returning plastic session {} for {}", plastic, chatId);
		return plastic;
	}

	public void deleteSession(Long chatId) {
		PlasticDto plastic = plasticSession.get(chatId);
		if (plastic == null) {
			log.warn("No plastic session for user {}", chatId);
			return;
		}
		log.info("Deleting plastic session for {}", chatId);
		plasticSession.remove(chatId);
	}

	public void createNewSession(Long chatId) {
		log.info("Creating new plastic session for {}", chatId);
		plasticSession.put(chatId, new PlasticDto());
	}

	public void addSession(Long chatId, PlasticDto dto) {
		log.info("Adding new session {} to user {}", dto, chatId);
		plasticSession.put(chatId, dto);
	}

	public void addBrand(Long chatId, String name) {
		PlasticDto plastic = plasticSession.get(chatId);
		if (plastic == null) {
			log.warn("No plastic session for user {}", chatId);
			return;
		}
		log.info("Setting plastic name '{}' for session {}", name, chatId);
		plastic.setBrand(name);
	}

	public void addDescription(Long chatId, String desc) {
		PlasticDto plastic = plasticSession.get(chatId);
		if (plastic == null) {
			log.warn("No plastic session for user {}", chatId);
			return;
		}
		log.info("Setting plastic description '{}' for session {}", desc, chatId);
		plastic.setDescription(desc);
	}

	public void addType(Long chatId, PlasticType type) {
		PlasticDto plastic = plasticSession.get(chatId);
		if (plastic == null) {
			log.warn("No plastic session for user {}", chatId);
			return;
		}
		log.info("Setting plastic type '{}' for session {}", type, chatId);
		plastic.setType(type);
	}

	public void addColor(Long chatId, PlasticColor color) {
		PlasticDto plastic = plasticSession.get(chatId);
		if (plastic == null) {
			log.warn("No plastic session for user {}", chatId);
			return;
		}
		log.info("Setting plastic color '{}' for session {}", color, chatId);
		plastic.setColor(color);
	}

	public void addAvailability(Long chatId, boolean available) {
		PlasticDto plastic = plasticSession.get(chatId);
		if (plastic == null) {
			log.warn("No plastic session for user {}", chatId);
			return;
		}
		log.info("Setting plastic availability '{}' for session {}", available, chatId);
		plastic.setAvailable(available);
	}
}
