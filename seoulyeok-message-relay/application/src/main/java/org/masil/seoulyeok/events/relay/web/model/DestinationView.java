package org.masil.seoulyeok.events.relay.web.model;

import lombok.Value;
import org.masil.seoulyeok.events.destination.DestinationStatus;
import org.masil.seoulyeok.events.destination.DestinationType;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class DestinationView {

    Long id;
    String address;
    DestinationType type;
    DestinationStatus status;
    Long lag;
    LocalDateTime latestReliedAt;
}
