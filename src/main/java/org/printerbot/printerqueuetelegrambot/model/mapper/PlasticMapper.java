package org.printerbot.printerqueuetelegrambot.model.mapper;

import org.mapstruct.Mapper;
import org.printerbot.printerqueuetelegrambot.model.dao.PlasticEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlasticMapper {
	PlasticEntity toPlasticEntity(PlasticDto dto);
	PlasticDto toPlasticDto(PlasticEntity entity);

	List<PlasticDto> toPlasticDtoList(List<PlasticEntity> entityList);

	List<PlasticEntity> toPlasticEntityList(List<PlasticDto> dtoList);
}
