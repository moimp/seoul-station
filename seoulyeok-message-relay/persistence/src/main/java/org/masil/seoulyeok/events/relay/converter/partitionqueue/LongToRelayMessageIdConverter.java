package org.masil.seoulyeok.events.relay.converter.partitionqueue;

import com.trevari.events.relaymessage.RelayMessageId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class LongToRelayMessageIdConverter implements Converter<Long, RelayMessageId> {
    @Override
    public RelayMessageId convert(Long source) {
        return RelayMessageId.of(source);
    }
}
