package org.masil.seoulyeok.ref.pipeline;

import lombok.Value;

import java.util.List;
import java.util.stream.Stream;

@Value(staticConstructor = "of")
public class Events {
    List<Event> events;

    public Stream<Event> stream() {
        return events.stream();
    }
}
