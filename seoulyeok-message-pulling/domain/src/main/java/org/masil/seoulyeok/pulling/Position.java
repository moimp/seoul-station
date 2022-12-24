package org.masil.seoulyeok.pulling;

import lombok.Value;

import java.time.LocalDateTime;


@Value(staticConstructor = "of")
public class Position {
    LocalDateTime value;

    public Position getTheLatestPositionBetween(LocalDateTime target){
        if (value.isBefore(target)){
            return Position.of(target);
        }
        return Position.of(value);

    }
}
