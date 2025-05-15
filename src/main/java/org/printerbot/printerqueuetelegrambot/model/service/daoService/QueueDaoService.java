package org.printerbot.printerqueuetelegrambot.model.service.daoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.model.dao.PrinterEntity;
import org.printerbot.printerqueuetelegrambot.model.dao.QueueEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.exceptions.QueueNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.mapper.QueueMapper;
import org.printerbot.printerqueuetelegrambot.model.repository.QueueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueDaoService implements DaoService<QueueEntity, QueueDto> {

	private final QueueRepository queueRepository;

	private final QueueMapper mapper;

	@Override
	public QueueEntity getEntityById(Long id) {
		log.info("Getting QueueEntity with id {}", id);
		return findById(id);
	}


	public List<QueueEntity> getEntitiesByUsername(String username) {
		log.info("Getting all QueueEntities with username {}", username);
		return queueRepository.findAllByUsername(username);
	}

	@Override
	public QueueDto getById(Long id) {
		log.info("Getting QueueDto with id {}", id);
		return mapper.toQueueDto(findById(id));
	}

	public List<QueueDto> getByUsername(String username) {
		log.info("Getting all QueueDto with username {}", username);
		return mapper.toQueueDtoList(queueRepository.findAllByUsername(username));
	}



	@Override
	public List<QueueDto> getAll() {
		log.info("Getting all queue");
		return mapper.toQueueDtoList(queueRepository.findAll());
	}

	public List<QueueEntity> getAllByPrinterId(PrinterEntity printerEntity) {
		log.info("Getting all queue by printer: {}", printerEntity);
		return queueRepository.findAllByPrinter(printerEntity);
	}

	@Override
	public void removeById(Long id) {
		log.info("Deleting QueueEntity with id {}", id);
		queueRepository.delete(findById(id));
	}

	public void remove(QueueEntity entity) {
		log.info("Deleting QueueEntity: {}", entity);
		queueRepository.delete(entity);
	}

	@Override
	public void save(QueueDto queueDto) {
		log.info("Saving QueueDto: {}", queueDto);
		queueDto.setId(null);
		queueRepository.save(mapper.toQueueEntity(queueDto));
	}

	@Override
	public void updateEntity(Long id, Consumer<QueueEntity> updateFunction) {
		log.info("Starting updating QueueEntity with id {}", id);
		QueueEntity entity = findById(id);
		updateFunction.accept(entity);
		queueRepository.save(entity);
	}

	private QueueEntity findById(Long id) {
		return queueRepository.findById(id).orElseThrow(
				() -> new QueueNotFoundException("Printer with id " + id + "is not found")
		);
	}
}
