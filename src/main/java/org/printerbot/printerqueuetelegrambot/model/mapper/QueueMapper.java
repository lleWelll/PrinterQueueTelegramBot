package org.printerbot.printerqueuetelegrambot.model.mapper;

import org.mapstruct.Mapper;
import org.printerbot.printerqueuetelegrambot.model.dao.QueueEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QueueMapper {
	QueueEntity toQueueEntity(QueueDto dto);

	QueueDto toQueueDto(QueueEntity entity);

	List<QueueDto> toQueueDtoList(List<QueueEntity> entityList);
}
