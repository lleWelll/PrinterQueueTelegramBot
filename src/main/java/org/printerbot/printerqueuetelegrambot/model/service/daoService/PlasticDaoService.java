package org.printerbot.printerqueuetelegrambot.model.service.daoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.model.dao.PlasticEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.exceptions.PlasticNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.mapper.PlasticMapper;
import org.printerbot.printerqueuetelegrambot.model.repository.PlasticRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlasticDaoService implements DaoService<PlasticEntity, PlasticDto> {

	private final PlasticRepository plasticRepository;

	private final PlasticMapper mapper;

	@Override
	public PlasticEntity getEntityById(Long id) {
		log.info("Getting PlasticEntity with id {}", id);
		return findById(id);
	}

	public List<PlasticEntity> getAllEntitiesById(List<Long> ids) {
		log.info("Getting List of PlasticEntities with ids: {}", ids);
		return plasticRepository.findAllById(ids);
	}

	@Override
	public PlasticDto getById(Long id) {
		log.info("Getting PlasticDto with id {}", id);
		return mapper.toPlasticDto(findById(id));
	}

	@Override
	public List<PlasticDto> getAll() {
		log.info("Getting all plastic");
		return mapper.toPlasticDtoList(plasticRepository.findAll());
	}

	public List<PlasticDto> getAllAvailablePrinters() {
		log.info("Getting All available PlasticDto");
		return mapper.toPlasticDtoList(plasticRepository.findAllByAvailableTrue());
	}

	@Override
	public void removeById(Long id) {
		log.info("Deleting PlasticEntity with id {}", id);
		plasticRepository.delete(findById(id));
	}

	@Override
	public void save(PlasticDto dto) {
		log.info("Saving PlasticEntity: {}", dto);
		dto.setId(null);
		plasticRepository.save(mapper.toPlasticEntity(dto));
	}

	@Override
	public void updateEntity(Long id, Consumer<PlasticEntity> updateFunction) {
		log.info("Starting updating PlasticEntity with id {}", id);
		PlasticEntity entity = findById(id);
		updateFunction.accept(entity);
		plasticRepository.save(entity);
	}

	private PlasticEntity findById(Long id) {
		return plasticRepository.findById(id).orElseThrow(
				() -> new PlasticNotFoundException("PlasticEntity with id " + id + "is not found")
		);
	}
}
