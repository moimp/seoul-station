package org.masil.seoulyeok.events.relay.converter.partitionqueue;

import com.trevari.events.relaymessage.RelayMessageId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class RelayMessageIdToLongConverter implements Converter<RelayMessageId, Long> {
    @Override
    public Long convert(RelayMessageId source) {
        return source.getValue();
    }
}
