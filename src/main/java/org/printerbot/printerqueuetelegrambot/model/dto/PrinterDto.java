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

	public String getFullInfo() {
		return new StringBuilder()
				.append("Id: ")
				.append(id)
				.append(" brand: ")
				.append(brand)
				.append(" model: ")
				.append(model)
				.append(" features: ")
				.append(features)
				.append(" available: ")
				.append(available)
				.append(" maxPlasticCapacity: ")
				.append(maxPlasticCapacity)
				.append(" supportedPlastic: ")
				.append(supported_plastic.stream().map(PlasticDto::getPlasticInfoWithId).toList())
				.toString();
	}

	public String getPrinterInfo() {
		return brand + " " + model;
	}
}
