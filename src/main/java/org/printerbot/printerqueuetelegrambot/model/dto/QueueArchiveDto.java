package org.printerbot.printerqueuetelegrambot.model.dto;

import lombok.Data;
import org.printerbot.printerqueuetelegrambot.model.enums.Status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class QueueArchiveDto {
	private Long id;
	private String username;
	private Long chatId;
	private LocalDateTime joinedAt;
	private Status printingStatus;
	private PrinterDto printer;
	private String stlModelPath;
	private String stlModelName;

	public String getQueueInfo() {
		return new StringBuilder()
				.append("<b>Printer:</b> ")
				.append(printer.getPrinterInfo())
				.append(" <b>model:</b> ")
				.append(stlModelName == null ? "Not uploaded" : stlModelName)
				.append(" <b>joined:</b> ")
				.append(getFormattedJoinedAt())
				.append(" <b>status:</b> ")
				.append(printingStatus)
				.toString();
	}

	public String getQueueInfoWithUsername() {
		return new StringBuilder()
				.append("<b>User:</b> @")
				.append(username)
				.append(" ")
				.append(getQueueInfo())
				.toString();
	}

	public String getFullQueueInfo() {
		return new StringBuilder()
				.append("<b>Id:</b> ")
				.append(id)
				.append(" ")
				.append(getQueueInfoWithUsername())
				.toString();
	}

	public String getFormattedJoinedAt() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
		return joinedAt.format(formatter);
	}
}
