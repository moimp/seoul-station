package com.trevari.messages;

import com.trevari.identity.generator.IdGeneratorFactory;
import com.trevari.identity.generator.LongIdGeneratorHolder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EventValidatorTest {

    EventValidator eventValidator;

    @BeforeEach
    void setUp() {
        IdGeneratorFactory idGeneratorFactory = new IdGeneratorFactory(() -> 123);
        LongIdGeneratorHolder.set(idGeneratorFactory.create());
    }

    @Test
    void success() {
        FooMessages payload = new FooMessages();
        payload.setFoo("ABC");

        eventValidator = new EventValidator();
        eventValidator.validateAndThrow(payload);
    }

    @Test
    void blank_fail() {
        FooMessages payload = new FooMessages();
        payload.setFoo("");

        eventValidator = new EventValidator();
        assertThatThrownBy(() -> eventValidator.validateAndThrow(payload)).isInstanceOf(EventValidateException.class);
    }

    @Test
    void null_fail() {
        FooMessages payload = new FooMessages();
        payload.setFoo(null);

        eventValidator = new EventValidator();
        assertThatThrownBy(() -> eventValidator.validateAndThrow(payload)).isInstanceOf(EventValidateException.class);
    }

    @Data
    @NoArgsConstructor
    static class FooMessages {
        @NotBlank
        @NotNull
        private String foo;
    }
}
