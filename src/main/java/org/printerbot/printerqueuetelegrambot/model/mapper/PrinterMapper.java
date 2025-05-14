package org.printerbot.printerqueuetelegrambot.model.mapper;

import org.mapstruct.Mapper;
import org.printerbot.printerqueuetelegrambot.model.dao.PrinterEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrinterMapper{

	PrinterEntity toPrinterEntity(PrinterDto dto);

	PrinterDto toPrinterDto(PrinterEntity entity);

	List<PrinterDto> toPrinterDtoList(List<PrinterEntity> entityList);
}
