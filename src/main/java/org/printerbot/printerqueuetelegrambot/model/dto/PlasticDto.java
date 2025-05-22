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
				.append("<b>Id:</b> ")
				.append(id)
				.append(" <b>brand:</b> ")
				.append(brand)
				.append(" <b>type:</b> ")
				.append(type)
				.append(" <b>color:</b> ")
				.append(color)
				.append(" <b>available:</b> ")
				.append(available)
				.append(" <b>description:</b> ")
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
