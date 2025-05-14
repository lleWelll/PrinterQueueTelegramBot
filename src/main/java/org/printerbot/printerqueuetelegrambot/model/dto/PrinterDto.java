package org.printerbot.printerqueuetelegrambot.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PrinterDto {
	private Long id;
	private String brand;
	private String model;
	private int maxPlasticCapacity;
	private String features;
	private boolean available;
	List<PlasticDto> supported_plastic;

	public String getPrinterInfo() {
		return brand + " " + model;
	}
}
