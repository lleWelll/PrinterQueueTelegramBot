package org.printerbot.printerqueuetelegrambot.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.model.dao.PlasticEntity;
import org.printerbot.printerqueuetelegrambot.model.dao.PrinterEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueArchiveDto;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.enums.Status;
import org.printerbot.printerqueuetelegrambot.model.exceptions.QueueNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.mapper.QueueMapper;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.QueueArchiveDaoService;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.QueueDaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueService {

	private final QueueDaoService queueDaoService;

	private final QueueArchiveDaoService queueArchiveDaoService;

	private final QueueMapper mapper;

	@Transactional
	public void next(Long printerId) throws IndexOutOfBoundsException {
		log.info("Processing next method");
		List<QueueDto> allQueue = getAllQueue().stream()
				.filter(dto -> dto.getPrinter().getId().equals(printerId))
				.toList();

		if (allQueue.isEmpty()) {
			log.info("No entries in queue for printer {}", printerId);
			throw new RuntimeException(ConstantMessages.QUEUE_IS_EMPTY_MESSAGE.getMessage());
		}

		QueueDto current = allQueue.get(0);
		Status oldStatus = current.getPrintingStatus();
		Status newStatus = (oldStatus == Status.WAITING)
				? Status.PRINTING
				: Status.DONE;

		queueDaoService.updateEntity(current.getId(),
				e -> e.setPrintingStatus(newStatus));

		if (newStatus == Status.DONE) {
			queueDaoService.removeById(current.getId());
			queueArchiveDaoService.save(mapper.toQueueArchiveDto(current));
		}

		if (newStatus == Status.DONE && allQueue.size() > 1) {
			QueueDto second = allQueue.get(1);
			queueDaoService.updateEntity(second.getId(),
					e -> e.setPrintingStatus(Status.PRINTING));
		}
	}

	public QueueDto getFirst(Long printerId) {
		log.info("Getting first queueDto");
		return getAllQueue().stream().filter(dto -> dto.getPrinter().getId().equals(printerId)).findFirst().orElseThrow(
				() -> new QueueNotFoundException("Queue is empty")
		);
	}

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

	public List<QueueArchiveDto> getAllArchive() {
		List<QueueArchiveDto> queueArchiveDtoList = queueArchiveDaoService.getAll();
		return queueArchiveDtoList.stream().sorted(
				Comparator.comparing(QueueArchiveDto::getJoinedAt)
		).toList();
	}

	public void joinQueue(String username, Long chatId, QueueDto dto) {
		dto.setUsername(username);
		dto.setChatId(chatId);
		dto.setPrintingStatus(Status.WAITING);
		queueDaoService.save(dto);
	}

	public void leaveQueue(QueueDto dto) {
		log.info("User {} leaving queue: {}", dto.getUsername(), dto.getQueueInfo());
		queueDaoService.remove(mapper.toQueueEntity(dto));
	}

	public List<QueueDto> getQueueListByUsernameWherePrintingStatusIsWaiting(String username) {
		return queueDaoService.getByUsername(username).stream()
				.filter(entity -> entity.getPrintingStatus() == Status.WAITING)
				.toList();
	}

	private boolean isPlasticSupported(PrinterEntity printer, List<Long> plasticIds) {
		Set<Long> supportedIdSet = printer.getSupported_plastic().stream()
				.map(PlasticEntity::getId)
				.collect(Collectors.toSet());
		return supportedIdSet.containsAll(plasticIds);
	}

	private QueueDto getSecondQueue(List<QueueDto> queueDtoList) {
		if (queueDtoList.size() >= 2) {
			return queueDtoList.get(1);
		} else {
			return null;
		}
	}

}
