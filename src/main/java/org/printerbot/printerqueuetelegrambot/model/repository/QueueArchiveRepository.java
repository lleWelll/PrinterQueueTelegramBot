package org.printerbot.printerqueuetelegrambot.model.repository;

import org.printerbot.printerqueuetelegrambot.model.dao.PrinterEntity;
import org.printerbot.printerqueuetelegrambot.model.dao.QueueArchiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueArchiveRepository extends JpaRepository<QueueArchiveEntity, Long> {

	List<QueueArchiveEntity> findAllByUsername(String username);

	List<QueueArchiveEntity> findAllByPrinter(PrinterEntity printerEntity);

}
