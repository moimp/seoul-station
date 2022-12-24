package org.masil.seoulyeok.pulling.incoming.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringDataEventRepository extends CrudRepository<EventsJdbcEntity, Long> {

    @Query("SELECT * FROM events AS e WHERE e.occurred_at > :occurredAt AND e.event_type in (:eventTypes) ORDER BY e.occurred_at LIMIT :size")
    List<EventsJdbcEntity> findAllEventsFromLatestOccurredAtAndSize(@Param("eventTypes") List<String> eventTypes, @Param("occurredAt") LocalDateTime occurredAt, @Param("size") Long size);
}
