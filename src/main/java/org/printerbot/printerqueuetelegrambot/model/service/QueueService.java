package org.printerbot.printerqueuetelegrambot.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.model.dao.PlasticEntity;
import org.printerbot.printerqueuetelegrambot.model.dao.PrinterEntity;
import org.printerbot.printerqueuetelegrambot.model.dao.QueueEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.enums.Status;
import org.printerbot.printerqueuetelegrambot.model.exceptions.PlasticIsNotSupportedException;
import org.printerbot.printerqueuetelegrambot.model.exceptions.QueueNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PlasticDaoService;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PrinterDaoService;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.QueueDaoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueService {

	private final QueueDaoService queueDaoService;

	private final PrinterDaoService printerDaoService;

	private final PlasticDaoService plasticDaoService;

	public void joinQueue(String username, QueueDto dto) {
		dto.setUsername(username);
		dto.setPrintingStatus(Status.WAITING);
		log.info("Joining queue with dto: {}", dto);
		queueDaoService.save(dto);
	}

	public void leaveQueue(String username, int index) {
		log.info("User {} leaving queue", username);
		List<QueueEntity> queueEntities = queueDaoService.getEntitiesByUsername(username);
		if (index >= queueEntities.size()) {
			throw new QueueNotFoundException("Queue entry not found: user '" + username + "' has no record at index " + index);
		}
		queueDaoService.remove(queueEntities.get(index));
	}

	public void modifyQueue(String username, int index) {

	}

	private boolean isPlasticSupported(PrinterEntity printer, List<Long> plasticIds) {
		Set<Long> supportedIdSet = printer.getSupported_plastic().stream()
				.map(PlasticEntity::getId)
				.collect(Collectors.toSet());
		return supportedIdSet.containsAll(plasticIds);
	}

}
