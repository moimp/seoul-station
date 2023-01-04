package org.masil.seoulyeok.events.relay.api;

import org.masil.seoulyeok.events.relay.application.MessageSender;
import org.masil.seoulyeok.events.relay.models.RelayRequest;
import org.masil.seoulyeok.events.relay.models.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SendMessagesController implements RelayApi {

    private final MessageSender sender;

    @Override
    public ResponseEntity<Result> deliveryToDestination(RelayRequest relayRequest) {
        sender.doWork(relayRequest);
        return ResponseEntity.ok(new Result().code(0).message("ok"));
    }
}
