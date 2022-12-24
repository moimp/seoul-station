package org.masil.seoulyeok.pulling.incoming.persistence;

import lombok.Value;

@Value(staticConstructor = "of")
public class EventPayload {

    String value;
}
