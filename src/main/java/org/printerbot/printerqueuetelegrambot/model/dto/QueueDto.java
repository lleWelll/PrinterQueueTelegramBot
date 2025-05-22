package org.printerbot.printerqueuetelegrambot.model.dto;

import lombok.Data;
import org.printerbot.printerqueuetelegrambot.model.dao.PlasticEntity;
import org.printerbot.printerqueuetelegrambot.model.dao.PrinterEntity;
import org.printerbot.printerqueuetelegrambot.model.enums.Status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class QueueDto {
	private Long id;
	private String username;
	private Long chatId;
	private LocalDateTime joinedAt;
	private Status printingStatus;
	private PrinterDto printer;
	private List<PlasticDto> plastics;
	private String stlModelPath;
	private String stlModelName;

	public String getQueueInfo() {
		return new StringBuilder()
				.append("Printer: ")
				.append(printer.getPrinterInfo())
				.append(" plastic: ")
				.append(getPlasticInfo())
				.append(" model: ")
				.append(stlModelName == null ? "Not uploaded" : stlModelName)
				.append(" joined: ")
				.append(getFormattedJoinedAt())
				.append(" status: ")
				.append(printingStatus)
				.toString();
	}

	public String getQueueInfoWithUsername() {
		return new StringBuilder()
				.append(" username: @")
				.append(username)
				.append(" ")
				.append(getQueueInfo())
				.toString();
	}

	public String getFullQueueInfo() {
		return new StringBuilder()
				.append("Id: ")
				.append(id)
				.append(" ")
				.append(getQueueInfoWithUsername())
				.toString();
	}

	public String getFormattedJoinedAt() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
		return joinedAt.format(formatter);
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
