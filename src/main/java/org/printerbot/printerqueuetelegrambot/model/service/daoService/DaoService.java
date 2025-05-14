package org.printerbot.printerqueuetelegrambot.model.service.daoService;

import java.util.List;
import java.util.function.Consumer;

public interface DaoService<Entity, Dto> {

	Entity getEntityById(Long id);

	Dto getById(Long id);

	List<Dto> getAll();

	void removeById(Long id);

	void save(Dto dto);

	void updateEntity(Long id, Consumer<Entity> updateFunction);

}
