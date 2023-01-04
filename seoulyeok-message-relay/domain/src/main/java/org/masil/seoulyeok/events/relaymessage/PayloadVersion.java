package org.masil.seoulyeok.events.relaymessage;

import lombok.Value;

@Value
public class PayloadVersion {

    public static PayloadVersion ZERO = new PayloadVersion("0.0.0");

    private static final String SEMVER_REGEX = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)"
            + "(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)"
            + "(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?"
            + "(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?$";

    public static PayloadVersion of(String value) {
        if (!value.matches(SEMVER_REGEX)) {
            throw new IllegalArgumentException("payload version is not semantic version");
        }
        return new PayloadVersion(value);
    }

    String value;

    private PayloadVersion(String value) {
        this.value = value;
    }
}
