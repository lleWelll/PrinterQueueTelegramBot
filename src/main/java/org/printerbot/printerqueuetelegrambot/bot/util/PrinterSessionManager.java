package org.printerbot.printerqueuetelegrambot.bot.util;

import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PrinterSessionManager {

	private final Map<Long, PrinterDto> printerSession = new HashMap<>();

	public PrinterDto getSession(Long chatId) {
		PrinterDto printer = printerSession.get(chatId);
		if (printer == null) {
			log.warn("No printer session for user {}", chatId);
			return null;
		}
		return printer;
	}

	public void deleteSession(Long chatId) {
		PrinterDto printer = printerSession.get(chatId);
		if (printer == null) {
			log.warn("No printer session for user {}, nothing to delete", chatId);
			return;
		}
		printerSession.remove(chatId);
	}

	public void createNewSession(Long chatId) {
		log.info("Creating new printer session for {}", chatId);
		printerSession.put(chatId, new PrinterDto());
	}

	public void createIfNotExists(Long chatId) {
		printerSession.computeIfAbsent(chatId, id -> {
			log.info("There is no session for {}, creating new", chatId);
			return new PrinterDto();
		});
	}

	public void addSession(Long chatId, PrinterDto dto) {
		log.info("Adding new session {} to user {}", dto.getPrinterInfo(), chatId);
		printerSession.put(chatId, dto);
	}

	private PrinterDto get(Long chatId) {
		createIfNotExists(chatId);
		return printerSession.get(chatId);
	}

	public void addBrand(Long chatId, String brand) {
		PrinterDto printer = get(chatId);
		log.info("Adding brand {} to printer session: {}", brand, chatId);
		printer.setBrand(brand);
	}

	public void addModel(Long chatId, String model) {
		PrinterDto printer = get(chatId);
		log.info("Adding model '{}' to printer session: {}", model, chatId);
		printer.setModel(model);
	}

	public void addFeatures(Long chatId, String features) {
		PrinterDto printer = get(chatId);
		log.info("Adding features '{}' to printer session: {}", features, chatId);
		printer.setFeatures(features);
	}

	public void addMaximumPlastic(Long chatId, int max) {
		PrinterDto printer = get(chatId);
		log.info("Adding maximumPlasticCapacity '{}' to printer session: {}", max, chatId);
		printer.setMaxPlasticCapacity(max);
	}

	public void addAvailability(Long chatId, boolean availability) {
		PrinterDto printer = get(chatId);
		log.info("Adding availability '{}' to printer session: {}", availability, chatId);
		printer.setAvailable(availability);
	}

	public void addSupportedPlastic(Long chatId, List<PlasticDto> plasticDtoList) {
		PrinterDto printer = get(chatId);
		log.info("Adding supportedPlastic '{}' to printer session {}", plasticDtoList, chatId);
		printer.setSupported_plastic(plasticDtoList);
	}
}
