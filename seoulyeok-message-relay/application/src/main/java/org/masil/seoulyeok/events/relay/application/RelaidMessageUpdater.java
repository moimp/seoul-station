package org.masil.seoulyeok.events.relay.application;

import org.masil.seoulyeok.events.relay.port.out.RelayMessageMarkPort;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.events.relaymessage.RelayMessageId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelaidMessageUpdater {

    private final RelayMessageMarkPort messageMarkPort;

    public boolean setMarkRelaidMessage(RelayMessageId relayMessageId, LocalDateTime publish) {
        return messageMarkPort.setReliedMark(relayMessageId, publish);
    }
}
