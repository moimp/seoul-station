package org.masil.seoulyeok.events.relay.util;

import org.junit.jupiter.api.Test;

class SerializerTest {

    @Test
    void AssignedEventMessage_에_대한_Serialize_Deserialize() {
//        RelayMessage message = RelayMessage.of(Seq.of(1L), DomainEventId.of(2L), DestinationId.of(3L));
//
//        String serialized = Serializer.getInstance().serialize(message);
//        assertThat(serialized).isEqualTo("{\"seq\":1,\"eventId\":2,\"destinationId\":3}");
//
//        RelayMessage deserialized = Serializer.getInstance()
//                .deserialize(serialized, RelayMessage.class);
//        assertThat(message).isEqualTo(deserialized);
    }

    @Test
    void PublishedEventMessage_에_대한_serialize_deserialize() {
//        PublishedEventMessage message = PublishedEventMessage.of(PublishedEventMessageId.of(1L), Seq.of(1L), DomainEventId.of(2L),
//                DestinationId.of(3L),
//                LocalDateTime.of(2022, 2, 2, 10, 10, 10), true);
//
//        String serialized = Serializer.getInstance().serialize(message);
//        assertThat(serialized).isEqualTo("{\"id\":1,\"seq\":1,\"eventId\":2,\"destinationId\":3,\"publishedAt\":\"2022-02-02T10:10:10\",\"success\":\"true\"}");
//
//        PublishedEventMessage deserialized = Serializer.getInstance()
//                .deserialize(serialized, PublishedEventMessage.class);
//
//        assertThat(message).isEqualTo(deserialized);
    }
}
