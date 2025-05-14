package org.printerbot.printerqueuetelegrambot.model.repository;

import org.printerbot.printerqueuetelegrambot.model.dao.PlasticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlasticRepository extends JpaRepository<PlasticEntity, Long> {
	List<PlasticEntity> findAllByAvailableTrue();
}
