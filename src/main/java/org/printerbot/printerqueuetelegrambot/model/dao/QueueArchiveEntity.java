package org.printerbot.printerqueuetelegrambot.model.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.printerbot.printerqueuetelegrambot.model.enums.Status;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "queue_archive")
@Data
public class QueueArchiveEntity extends EntityBaseClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "queue_id")
	private Long id;

	@Column(nullable = false, length = 100, name = "user")
	private String username;

	@Column(nullable = false, name = "chat_id")
	private Long chatId;

	@CreationTimestamp
	@Column(name = "join_time")
	private LocalDateTime joinedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status printingStatus;

	@Column(length = 256, name = "stl_model_path")
	private String stlModelPath;

	@Column(length = 100, name = "stl_model_name")
	private String stlModelName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "printer_id", nullable = false)
	private PrinterEntity printer;
}
