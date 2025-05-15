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
import org.printerbot.printerqueuetelegrambot.model.mapper.QueueMapper;
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

	private final QueueMapper mapper;

	public void joinQueue(String username, QueueDto dto) {
		dto.setUsername(username);
		dto.setPrintingStatus(Status.WAITING);
		log.info("Joining queue with dto: {}", dto);
		queueDaoService.save(dto);
	}

	public void leaveQueue(QueueDto dto) {
		log.info("User {} leaving queue: {}", dto.getUsername(), dto.getQueueInfo());
		queueDaoService.remove(mapper.toQueueEntity(dto));
	}

	public void modifyQueue(String username, int index) {

	}

	public List<QueueDto> getQueueListByUsername(String username) {
		return queueDaoService.getByUsername(username);
	}

	private boolean isPlasticSupported(PrinterEntity printer, List<Long> plasticIds) {
		Set<Long> supportedIdSet = printer.getSupported_plastic().stream()
				.map(PlasticEntity::getId)
				.collect(Collectors.toSet());
		return supportedIdSet.containsAll(plasticIds);
	}

}
