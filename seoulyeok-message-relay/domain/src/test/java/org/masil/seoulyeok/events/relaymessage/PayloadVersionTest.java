package org.masil.seoulyeok.events.relaymessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PayloadVersionTest {

    @Test
    void SemanticVersioning_형식이_아니라면_생성되지_않는다() {
        PayloadVersion actual = PayloadVersion.of("0.1.1");
        assertThat(actual).isNotNull();

        assertThatThrownBy(() -> PayloadVersion.of("123")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> PayloadVersion.of("1.1.1.1")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> PayloadVersion.of("1.1.")).isInstanceOf(IllegalArgumentException.class);
    }
}
