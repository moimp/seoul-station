package org.masil.seoulyeok.eventstore.port.incoming;

import java.util.List;

public interface RecordPublishedEventUseCase {

    boolean store(RecordedEventCommend commend);
    boolean store(List<RecordedEventCommend> commend);
}
