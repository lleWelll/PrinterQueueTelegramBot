package org.printerbot.printerqueuetelegrambot.model.repository;

import org.printerbot.printerqueuetelegrambot.model.dao.PrinterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrinterRepository extends JpaRepository<PrinterEntity, Long> {

	List<PrinterEntity> findAllByAvailableTrue();

}
