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
	private PrinterDto printer;
	private List<PlasticDto> plastics;

	public String getQueueInfo() {
		return new StringBuilder()
				.append("Joined: ")
				.append(joinedAt)
				.append(" status: ")
				.append(printingStatus)
				.append(" printer: ")
				.append(printer.getPrinterInfo())
				.append(" plastic: ")
				.append(getPlasticInfo())
				.toString();
	}

	private String getPlasticInfo() {
		StringBuilder builder = new StringBuilder();
		int counter = 0;
		for (var pl : plastics) {
			builder.append(pl.getPlasticInfo());
			if (counter < plastics.size() - 1) {
				builder.append(", ");
			}
		}
		return builder.toString();
	}
}
