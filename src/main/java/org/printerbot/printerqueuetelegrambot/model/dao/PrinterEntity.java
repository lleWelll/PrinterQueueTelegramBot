package org.printerbot.printerqueuetelegrambot.model.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "printers")
@Data
public class PrinterEntity extends EntityBaseClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="printer_id")
	private Long id;

	@Column(nullable = false, length = 100)
	private String brand;

	@Column(nullable = false, length = 100)
	private String model;

	@Column(nullable = false, name = "maximum_plastic_quantity")
	private int maxPlasticCapacity;

	private String features;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean available;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "printer_supported_plastic",
			joinColumns = @JoinColumn(name = "printer_id"),
			inverseJoinColumns = @JoinColumn(name = "plastic_id")
	)
	private List<PlasticEntity> supported_plastic;
}
