package org.printerbot.printerqueuetelegrambot.model.service.daoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.model.dao.PrinterEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
import org.printerbot.printerqueuetelegrambot.model.exceptions.PrinterNotFoundException;
import org.printerbot.printerqueuetelegrambot.model.mapper.PrinterMapper;
import org.printerbot.printerqueuetelegrambot.model.repository.PrinterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrinterDaoService implements DaoService<PrinterEntity, PrinterDto> {

	private final PrinterRepository printerRepository;

	private final PrinterMapper mapper;

	@Override
	public PrinterEntity getEntityById(Long id) {
		log.info("Getting PrinterEntity with id {}", id);
		return findById(id);
	}

	@Override
	public PrinterDto getById(Long id) {
		log.info("Getting PrinterDto with id {}", id);
		return mapper.toPrinterDto(findById(id));
	}

	@Override
	public List<PrinterDto> getAll() {
		log.info("Getting all PrinterDto");
		return mapper.toPrinterDtoList(printerRepository.findAll());
	}

	public List<PrinterDto> getAllAvailablePrinters() {
		log.info("Getting All available PrinterDto");
		return mapper.toPrinterDtoList(printerRepository.findAllByAvailableTrue());
	}

	@Override
	public void removeById(Long id) {
		log.info("Deleting PrinterEntity with id {}", id);
		printerRepository.delete(findById(id));
	}

	@Override
	public void save(PrinterDto printerDto) {
		log.info("Saving PrinterEntity: {}", printerDto);
		printerDto.setId(null);
		printerRepository.save(mapper.toPrinterEntity(printerDto));
	}

	@Override
	public void updateEntity(Long id, Consumer<PrinterEntity> updateFunction) {
		log.info("Starting updating PrinterEntity with id {}", id);
		PrinterEntity entity = findById(id);
		updateFunction.accept(entity);
		printerRepository.save(entity);
	}

	private PrinterEntity findById(Long id) {
		return printerRepository.findById(id).orElseThrow(
				() -> new PrinterNotFoundException("PrinterEntity with id " + id + "is not found")
		);
	}
}
