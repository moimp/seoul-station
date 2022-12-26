package org.masil.seoulyeok.events.handler;

import org.masil.commons.condition.Condition;
import org.masil.seoulyeok.events.core.Event;
import org.masil.seoulyeok.events.core.EventType;
import lombok.Value;

@Value(staticConstructor = "of")
public class EventTypeCondition implements Condition<Event> {

    EventType expected;

    @Override
    public boolean isSatisfy(Event factor) {
        return expected.equals(factor.getType());
    }
}
