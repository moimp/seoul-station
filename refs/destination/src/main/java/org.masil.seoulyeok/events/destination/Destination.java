package org.masil.seoulyeok.events.destination;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Destination implements DestinationTarget {

    public static Destination create(Address address, DestinationType type) {
        DestinationId id = DestinationId.of(1L);
        return new Destination(id, address, type, DestinationStatus.ACTIVE);
    }

    public static Destination of(DestinationId id, Address address, DestinationType type) {
        return new Destination(id, address, type, DestinationStatus.ACTIVE);
    }

    public static Destination of(DestinationId id, Address address, DestinationType type, DestinationStatus status) {
        return new Destination(id, address, type, status);
    }

    private final DestinationId id;
    private final Address address;
    private final DestinationType type;
    private DestinationStatus status;

    private Destination(DestinationId id, Address address, DestinationType type, DestinationStatus status) {
        this.id = id;
        this.address = address;
        this.type = type;
        this.status = status;
    }

    @Override
    public DestinationId getId() {
        return id;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public DestinationType getType() {
        return type;
    }

    public void inactive() {
        status = DestinationStatus.INACTIVE;
    }
}
