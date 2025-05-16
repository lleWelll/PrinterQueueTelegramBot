package org.printerbot.printerqueuetelegrambot.bot.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSessionManager {

	private final Map<Long, QueueDto> userSession = new HashMap<>();

	public void createNewSession(Long chatId) {
		log.info("Creating user {} session", chatId);
		userSession.put(chatId, new QueueDto());
	}

	public void deleteSession(Long chatId) {
		log.info("Deleting user {} session", chatId);
		if (! userSession.containsKey(chatId)) {
			log.info("user {} session does not exist", chatId);
			return;
		}
		userSession.remove(chatId);
	}

	public void addSession(Long chatId, QueueDto dto) {
		userSession.computeIfAbsent(chatId, id -> {
			log.info("No session for user {}, creating new one", id);
			return new QueueDto();
		});

		log.info("Adding new session {} to user {}", dto.getQueueInfo(), chatId);
		userSession.put(chatId, dto);
	}


	public void addSelectedPrinter(Long chatId, PrinterDto printer) {
		userSession.computeIfAbsent(chatId, id -> {
			log.info("No session for user {}, creating new one", id);
			return new QueueDto();
		});

		log.info("Adding printer {} to user {} session", printer, chatId);
		userSession.get(chatId).setPrinter(printer);
	}
	public void addSelectedPlastic(Long chatId, List<PlasticDto> plastic) {
		QueueDto session = userSession.get(chatId);

		if (session == null) {
			log.warn("No session for user {}, cannot add plastic", chatId);
			return;
		}

		if (session.getPrinter() == null) {
			log.warn("User {} has not selected a printer yet, cannot add plastic", chatId);
			return;
		}

		log.info("Adding plastic {} to user {} session", plastic, chatId);
		session.setPlastics(plastic);
	}

	public void addUploadedModelFile(Long chatId, String fileName, String filePath) {
		QueueDto session = userSession.get(chatId);

		if (session == null) {
			log.warn("No session for user {}, cannot file", chatId);
			return;
		}

		log.info("Adding File {}, {} to user {} session", fileName, filePath, chatId);
		session.setStlModelName(fileName);
		session.setStlModelPath(filePath);
	}

	public QueueDto getSession(Long chatId) {
		QueueDto session = userSession.get(chatId);
		if (session == null) {
			log.warn("No session for user {}, cannot add plastic", chatId);
			return null;
		}
		return session;
	}


	public PrinterDto getChosenPrinter(Long chatId) {
		QueueDto session = userSession.get(chatId);

		if (session == null) {
			log.warn("No session for user {}", chatId);
			return null;
		}

		return session.getPrinter();
	}

	public List<PlasticDto> getChosenPlastic(Long chatId) {
		QueueDto session = userSession.get(chatId);

		if (session == null) {
			log.warn("No session for user {}", chatId);
			return null;
		}

		if (session.getPrinter() == null) {
			log.warn("User {} has not selected a printer yet", chatId);
			return null;
		}

		return session.getPlastics();
	}



}
