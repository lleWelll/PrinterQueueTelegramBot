package org.printerbot.printerqueuetelegrambot.model.dto;

import lombok.Data;
import org.printerbot.printerqueuetelegrambot.model.enums.PlasticColor;
import org.printerbot.printerqueuetelegrambot.model.enums.PlasticType;

@Data
public class PlasticDto {
	private Long id;
	private String brand;
	private PlasticType type;
	private PlasticColor color;
	private boolean available;
	private String description;

	public String getPlasticFullInfo() {
		return new StringBuilder()
				.append("Id: ")
				.append(id)
				.append(" brand: ")
				.append(brand)
				.append(" type: ")
				.append(type)
				.append(" color: ")
				.append(color)
				.append(" available: ")
				.append(available)
				.append(" description: ")
				.append(description)
				.toString();
	}

	public String getPlasticInfoWithId() {
		return id + " " + getPlasticInfo();
	}

	public String getPlasticInfo() {
		return brand + " " + type + " " + color;
	}
}
