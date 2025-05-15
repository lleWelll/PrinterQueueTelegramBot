package org.printerbot.printerqueuetelegrambot.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.model.dao.PlasticEntity;
import org.printerbot.printerqueuetelegrambot.model.dao.PrinterEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.enums.Status;
import org.printerbot.printerqueuetelegrambot.model.exceptions.QueueNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.mapper.QueueMapper;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.QueueDaoService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueService {

	private final QueueDaoService queueDaoService;

	private final QueueMapper mapper;

	public int getMyPosition(Long queueId, List<QueueDto> allQueue) {
		int position = -1;
		for (int i = 0; i < allQueue.size(); i++) {
			var q = allQueue.get(i);
			if (q.getId().equals(queueId)) {
				position = i + 1;
			}
		}
		if (position < 1) {
			throw new QueueNotFoundException("This queue is does not exist");
		}
		return position;
	}

	public List<QueueDto> getAllQueueByPrinter(PrinterEntity printer) {
		return mapper.toQueueDtoList(queueDaoService.getAllByPrinterId(printer));
	}

	public List<QueueDto> getAllQueue() {
		List<QueueDto> queueDtoList = queueDaoService.getAll();
		return queueDtoList.stream().sorted(
				Comparator.comparing(QueueDto::getJoinedAt)
		).toList();
	}

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
