package org.masil.seoulyeok.eventstore.outgoing.persistence;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Slf4j
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @PersistenceConstructor)
@Table("events")
public class EventJdbcEntity {

    @Id
    private Long id;
    private String eventType;
    private EventPayload payload;
    private String payloadVersion;
    private LocalDateTime occurredAt;
    @CreatedDate
    private LocalDateTime publishedAt;
    @Version
    private Long version;

    public static EventJdbcEntity create(Long id, String eventType, EventPayload payload, String payloadVersion, LocalDateTime occurredAt) {
        return new EventJdbcEntity(id, eventType, payload, payloadVersion, occurredAt, LocalDateTime.now(), null);
    }
}
