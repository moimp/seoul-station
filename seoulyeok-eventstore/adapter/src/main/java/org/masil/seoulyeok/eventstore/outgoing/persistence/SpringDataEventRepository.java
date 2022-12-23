package org.masil.seoulyeok.eventstore.outgoing.persistence;

import org.springframework.data.repository.CrudRepository;

public interface SpringDataEventRepository extends CrudRepository<EventJdbcEntity, Long> {
}
