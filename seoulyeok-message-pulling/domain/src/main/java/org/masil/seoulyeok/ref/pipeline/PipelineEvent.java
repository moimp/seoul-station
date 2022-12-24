package org.masil.seoulyeok.ref.pipeline;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PipelineEvent {

    private List<Event> events;
    private LocalDateTime startingPoint;
    private Long size;

    public long getSize() {
        return size;
    }


    public List<String> getTargetEventTypes() {
        return events.stream()
                .map(Event::getType)
                .collect(Collectors.toUnmodifiableList());

    }
}
