package org.masil.seoulyeok.eventstore.service;

public class DuplicatedEventException extends RuntimeException {

    public DuplicatedEventException(String message) {
        super(message);
    }
}
