package org.masil.seoulyeok.eventstore.outgoing.persistence;

import lombok.Value;

@Value(staticConstructor = "of")
public class EventPayload {

    String value;
}
