package org.masil.seoulyeok.events.core;

import lombok.Value;

@Value(staticConstructor = "of")
public class EventId {

    Long id;
}
