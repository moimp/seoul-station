package org.masil.seoulyeok.eventstore.port.incoming;

import lombok.Value;

@Value(staticConstructor = "of")
public class GetEventCommand {

    Long eventId;
}
