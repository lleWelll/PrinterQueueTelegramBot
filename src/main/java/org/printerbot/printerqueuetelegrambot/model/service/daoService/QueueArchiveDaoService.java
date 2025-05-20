package org.printerbot.printerqueuetelegrambot.model.service.daoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.model.dao.QueueArchiveEntity;
import org.printerbot.printerqueuetelegrambot.model.dao.QueueEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueArchiveDto;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.printerbot.printerqueuetelegrambot.model.exceptions.QueueNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.mapper.QueueMapper;
import org.printerbot.printerqueuetelegrambot.model.repository.QueueArchiveRepository;
import org.printerbot.printerqueuetelegrambot.model.repository.QueueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueArchiveDaoService implements DaoService<QueueArchiveEntity, QueueArchiveDto> {

	private final QueueArchiveRepository queueRepository;

	private final QueueMapper mapper;

	@Override
	public QueueArchiveEntity getEntityById(Long id) {
		log.info("Getting QueueArchiveEntity with id {}", id);
		return findById(id);
	}

	@Override
	public QueueArchiveDto getById(Long id) {
		log.info("Getting QueueArchiveDto with id {}", id);
		return mapper.toQueueArchiveDto(findById(id));
	}

	public List<QueueArchiveDto> getByUsername(String username) {
		log.info("Getting all QueueArchiveDto with username {}", username);
		return mapper.toQueueArchiveDtoList(queueRepository.findAllByUsername(username));
	}

	@Override
	public List<QueueArchiveDto> getAll() {
		log.info("Getting all queueArchives");
		return mapper.toQueueArchiveDtoList(queueRepository.findAll());
	}

	@Override
	public void removeById(Long id) {
		log.info("Deleting QueueArchiveEntity with id {}", id);
		queueRepository.delete(findById(id));
	}

	public void remove(QueueArchiveEntity queueArchiveEntity) {
		log.info("Deleting QueueArchiveEntity: {}", queueArchiveEntity);
		queueRepository.delete(queueArchiveEntity);
	}

	@Override
	public void save(QueueArchiveDto queueArchiveDto) {
		queueArchiveDto.setId(null);
		log.info("Saving QueueArchiveDto: {}", queueArchiveDto);
		queueRepository.save(mapper.toQueueArchiveEntity(queueArchiveDto));
	}

	@Override
	public void updateEntity(Long id, Consumer<QueueArchiveEntity> updateFunction) {
		log.info("Starting updating QueueArchiveEntity with id {}", id);
		QueueArchiveEntity entity = findById(id);
		updateFunction.accept(entity);
		queueRepository.save(entity);
	}

	private QueueArchiveEntity findById(Long id) {
		return queueRepository.findById(id).orElseThrow(
				() -> new QueueNotFoundException("QueueArchive with id " + id + "is not found")
		);
	}
}
