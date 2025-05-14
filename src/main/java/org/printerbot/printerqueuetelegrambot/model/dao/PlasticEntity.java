package org.printerbot.printerqueuetelegrambot.model.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.printerbot.printerqueuetelegrambot.model.enums.PlasticColor;
import org.printerbot.printerqueuetelegrambot.model.enums.PlasticType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "plastic")
@Data
public class PlasticEntity extends EntityBaseClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plastic_id")
	private Long id;

	@Column(nullable = false, length = 100)
	private String brand;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 25)
	private PlasticType type;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 25)
	private PlasticColor color;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean available;

	private String description;
}
