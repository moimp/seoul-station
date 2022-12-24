package org.masil.seoulyeok.pulling;


import lombok.Value;

import javax.validation.constraints.NotNull;

@Value(staticConstructor = "of")
public class PulledOffsetId{
    @NotNull(message = "PulledOffsetId 는 null 이 될 수 없습니다.")
    Long id;

    public Long get() {
        return id;
    }
}
