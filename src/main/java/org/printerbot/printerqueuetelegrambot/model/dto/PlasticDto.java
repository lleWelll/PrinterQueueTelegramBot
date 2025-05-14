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

	public String getPlasticInfo() {
		return brand + " " + type + " " + color;
	}
}
