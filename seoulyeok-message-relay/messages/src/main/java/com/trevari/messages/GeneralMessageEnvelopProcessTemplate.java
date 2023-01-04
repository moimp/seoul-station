package com.trevari.messages;

import com.trevari.domain.core.Serializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

import static com.trevari.messages.GeneralMessageEnvelopProcessReturn.*;

@Slf4j
@RequiredArgsConstructor
public class GeneralMessageEnvelopProcessTemplate {

    private final EventValidator eventValidator;

    public GeneralMessageEnvelopProcessTemplate() {
        this.eventValidator = new EventValidator();
    }

    public <T> GeneralMessageEnvelopProcessReturn doProcess(GeneralMessageEnvelop<String> messageEnvelop, Class<T> aEventClass, Consumer<T> processor) {
        try {
            if (messageEnvelop == null || messageEnvelop.getPayload() == null) {

                log.error("GeneralMessageEnvelop or GeneralMessageEnvelop.payload is null");
                return IGNORE;
            }

            if (aEventClass == null) {
                log.error(String.format("aEventClass is null. MessageId is %s, messageEnvelop Payload is %s", messageEnvelop.getMessageId(), messageEnvelop.getPayload()));
                return RETRY;
            }

            if (processor == null) {
                log.error(String.format("processor is null. MessageId is %s, messageEnvelop Payload is %s", messageEnvelop.getMessageId(), messageEnvelop.getPayload()));
                return RETRY;
            }

            final String payload = messageEnvelop.getPayload();

            T deserialize = Serializer.getInstance().deserialize(payload, aEventClass);

            eventValidator.validateAndThrow(deserialize);

            processor.accept(deserialize);
            return SUCCESS;
        } catch (GeneralMessageEnvelopPayloadDeserializeException e) {
            log.error(String.format("Failed to deserialize MessageEnvelop. payload in MessageEnvelop is  %s, MessageId is %s",
                    e.getMessageEnvelop().getPayload(), e.getMessageEnvelop().getMessageId()), e);
            return IGNORE;
        } catch (EventValidateException e) {
            log.error(String.format("%s has violationMessages. Messages: '%s'", e.getEventType(), e.getViolationMessages()), e);
            return IGNORE;
        } catch (Exception e) {
            log.error(String.format("Failed to process of GeneralMessageEnvelop. MessageId is %s, Exception Message : %s", messageEnvelop.getMessageId(), e.getMessage()));
            return RETRY;
        }
    }
}
