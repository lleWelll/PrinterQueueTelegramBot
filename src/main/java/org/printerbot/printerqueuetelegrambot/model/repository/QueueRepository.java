package org.printerbot.printerqueuetelegrambot.model.repository;

import org.printerbot.printerqueuetelegrambot.model.dao.PrinterEntity;
import org.printerbot.printerqueuetelegrambot.model.dao.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, Long> {
	List<QueueEntity> findAllByUsername(String username);

	List<QueueEntity> findAllByPrinter(PrinterEntity printerEntity);

}
