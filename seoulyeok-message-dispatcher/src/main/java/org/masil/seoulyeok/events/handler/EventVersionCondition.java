package org.masil.seoulyeok.events.handler;

import org.masil.commons.condition.Condition;
import org.masil.seoulyeok.events.core.Event;
import org.masil.seoulyeok.events.core.EventVersion;
import lombok.Value;

@Value(staticConstructor = "of")
public class EventVersionCondition implements Condition<Event> {

    EventVersion expected;

    @Override
    public boolean isSatisfy(Event factor) {
        return expected.equals(factor.getVersion());
    }
}
