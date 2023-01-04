package org.masil.seoulyeok.events.destination;

public interface DestinationTarget {
    DestinationId getId();
    Address getAddress();
    DestinationType getType();
}
