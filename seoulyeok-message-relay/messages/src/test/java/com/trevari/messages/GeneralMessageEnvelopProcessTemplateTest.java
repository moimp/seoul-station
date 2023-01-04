package com.trevari.messages;

import com.trevari.domain.core.DomainEvent;
import com.trevari.domain.core.DomainEventId;
import com.trevari.domain.core.Serializer;
import com.trevari.identity.generator.IdGeneratorFactory;
import com.trevari.identity.generator.LongIdGeneratorHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralMessageEnvelopProcessTemplateTest {


    GeneralMessageEnvelopProcessTemplate sut = new GeneralMessageEnvelopProcessTemplate();

    @BeforeEach
    void setUp() {
        IdGeneratorFactory idGeneratorFactory = new IdGeneratorFactory(() -> 123);
        LongIdGeneratorHolder.set(idGeneratorFactory.create());
    }

    @Data
    @ToString
    static class S {
        DomainEventId eventId;
        DestinationId destinationId;
    }

    @Data
    @ToString
    static class DestinationId {
        long value;
    }

    @Test
    void IGNORE_when_messageEnvelop_is_null() {
        Consumer<FooMessages> processor = fooMessages -> System.out.println(fooMessages.getFoo());
        GeneralMessageEnvelopProcessReturn processReturn = sut.doProcess(null, FooMessages.class, processor);
        assertThat(processReturn).isEqualTo(GeneralMessageEnvelopProcessReturn.IGNORE);
    }

    @Test
    void IGNORE_when_eventClass_is_null() {
        GeneralMessageEnvelop<String> envelop = new GeneralMessageEnvelop<>(DomainEvent.of(new FooMessages("XXX")).serialize());
        Consumer<FooMessages> processor = fooMessages -> System.out.println(fooMessages.getFoo());
        GeneralMessageEnvelopProcessReturn processReturn = sut.doProcess(envelop, null, processor);
        assertThat(processReturn).isEqualTo(GeneralMessageEnvelopProcessReturn.RETRY);
    }

    @Test
    void RETRY_when_process_is_null_return() {
        GeneralMessageEnvelop<String> envelop = new GeneralMessageEnvelop<>(DomainEvent.of(new FooMessages("XXX")).serialize());
        GeneralMessageEnvelopProcessReturn processReturn = sut.doProcess(envelop, FooMessages.class, null);
        assertThat(processReturn).isEqualTo(GeneralMessageEnvelopProcessReturn.RETRY);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class FooMessages {
        @NotBlank
        @NotNull
        private String foo;
    }

    @Test
    void EventValidateException_when_create_with_empty_string() {
        DomainEvent event = DomainEvent.of(new FooMessages(""));
        GeneralMessageEnvelop<String> messageEnvelope = new GeneralMessageEnvelop<>(event.serialize());
        Consumer<FooMessages> processor = fooMessages -> System.out.println(fooMessages.getFoo());
        GeneralMessageEnvelopProcessReturn processReturn = sut.doProcess(messageEnvelope, FooMessages.class, processor);
        assertThat(processReturn).isEqualTo(GeneralMessageEnvelopProcessReturn.IGNORE);
    }

    @Test
    void EventValidateException_when_create_with_null_string() {
        DomainEvent event = DomainEvent.of(new FooMessages(null));
        GeneralMessageEnvelop<String> messageEnvelope = new GeneralMessageEnvelop<>(event.serialize());
        Consumer<FooMessages> processor = fooMessages -> System.out.println(fooMessages.getFoo());

        GeneralMessageEnvelopProcessReturn processReturn = sut.doProcess(messageEnvelope, FooMessages.class, processor);
        assertThat(processReturn).isEqualTo(GeneralMessageEnvelopProcessReturn.IGNORE);
    }


    @Test
    void DomainEventPayloadDeserializeException_when_DomainEvent_is_not_valid() {
        DomainEvent event = DomainEvent.of("xxxx");
        GeneralMessageEnvelop<String> messageEnvelope = new GeneralMessageEnvelop<>(event.serialize());
        Consumer<FooMessages> processor = fooMessages -> System.out.println(fooMessages.getFoo());

        GeneralMessageEnvelopProcessReturn processReturn = sut.doProcess(messageEnvelope, FooMessages.class, processor);
        assertThat(processReturn).isEqualTo(GeneralMessageEnvelopProcessReturn.IGNORE);
    }

}
