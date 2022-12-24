package org.masil.seoulyeok.ref.pipeline;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {
    String type;
    String version;
    LocalDateTime startingPoint;
}
