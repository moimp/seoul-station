package org.masil.seoulyeok.pulling;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class TargetEvents {

    List<String> value;

    public boolean containsEventType(String eventType){
        return value.contains(eventType);
    }
}
