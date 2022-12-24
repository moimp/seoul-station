package org.masil.seoulyeok.events;

import lombok.Value;

@Value(staticConstructor = "of")
public class EventId {

    Long eventId;

}
