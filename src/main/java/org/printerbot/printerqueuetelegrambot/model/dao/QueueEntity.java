package org.printerbot.printerqueuetelegrambot.model.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.printerbot.printerqueuetelegrambot.model.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "queue")
@Data
public class QueueEntity extends EntityBaseClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "queue_id")
	private Long id;

	@Column(nullable = false, length = 100, name = "user")
	private String username;

	@CreationTimestamp
	@Column(name = "join_time")
	private LocalDateTime joinedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status printingStatus;

	@ManyToOne
	@JoinColumn(name = "printer_id", nullable = false)
	private PrinterEntity printer;

	@ManyToMany
	@JoinTable(
			name = "queue_plastic",
			joinColumns = @JoinColumn(name = "queue_id"),
			inverseJoinColumns = @JoinColumn(name = "plastic_id")
	)
	private List<PlasticEntity> plastics;
}
