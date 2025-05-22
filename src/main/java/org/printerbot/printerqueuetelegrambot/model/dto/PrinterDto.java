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
				.append("<b>Id:</b> ")
				.append(id)
				.append(" <b>brand:</b> ")
				.append(brand)
				.append(" <b>model:</b> ")
				.append(model)
				.append(" <b>features:</b> ")
				.append(features)
				.append(" <b>available:</b> ")
				.append(available)
				.append(" <b>maxPlasticCapacity:</b> ")
				.append(maxPlasticCapacity)
				.append(" <b>supportedPlastic:</b> ")
				.append(supported_plastic.stream().map(PlasticDto::getPlasticInfoWithId).toList())
				.toString();
	}

	public String getPrinterInfo() {
		return brand + " " + model;
	}
}
