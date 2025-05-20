package org.printerbot.printerqueuetelegrambot.model.mapper;

import org.mapstruct.Mapper;
import org.printerbot.printerqueuetelegrambot.model.dao.QueueArchiveEntity;
import org.printerbot.printerqueuetelegrambot.model.dao.QueueEntity;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueArchiveDto;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QueueMapper {
	QueueEntity toQueueEntity(QueueDto dto);

	QueueDto toQueueDto(QueueEntity entity);

	List<QueueDto> toQueueDtoList(List<QueueEntity> entityList);


	QueueArchiveEntity toQueueArchiveEntity(QueueEntity queueEntity);

	QueueArchiveEntity toQueueArchiveEntity(QueueDto queueDto);

	QueueArchiveEntity toQueueArchiveEntity(QueueArchiveDto queueArchiveDto);

	QueueArchiveDto toQueueArchiveDto(QueueEntity queueEntity);

	QueueArchiveDto toQueueArchiveDto(QueueDto queueDto);

	QueueArchiveDto toQueueArchiveDto(QueueArchiveEntity queueArchiveEntity);

	List<QueueArchiveDto> toQueueArchiveDtoList(List<QueueArchiveEntity> queueArchiveEntities);
}
