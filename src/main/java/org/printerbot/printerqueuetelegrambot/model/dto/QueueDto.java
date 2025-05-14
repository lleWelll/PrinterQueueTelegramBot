package org.printerbot.printerqueuetelegrambot.model.dto;

import lombok.Data;
import org.printerbot.printerqueuetelegrambot.model.dao.PlasticEntity;
import org.printerbot.printerqueuetelegrambot.model.dao.PrinterEntity;
import org.printerbot.printerqueuetelegrambot.model.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QueueDto {
	private Long id;
	private String username;
	private LocalDateTime joinedAt;
	private Status printingStatus;
	private PrinterEntity printer;
	private List<PlasticEntity> plastics;
}
