package org.masil.seoulyeok.events.relay.web;

import org.masil.seoulyeok.events.destination.Destination;
import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.relay.port.out.LoadViewDestinationPort;
import org.masil.seoulyeok.events.relay.port.out.LoadViewRelayMessagePort;
import org.masil.seoulyeok.events.relay.web.model.DestinationView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DashBoardController {

    private final LoadViewDestinationPort loadWebViewDestinationPort;
    private final LoadViewRelayMessagePort loadViewRelayMessagePort;
    private DestinationId id;

    @GetMapping(value = "/dashboard")
    public ModelAndView games() {
        List<Destination> destinations = loadWebViewDestinationPort.findAllDestination();

        List<DestinationView> destinationViews = destinations.stream()
                .map(destination -> {
                    id = destination.getId();
                    long leg = loadViewRelayMessagePort.getLegByDestinationId(id);
                    LocalDateTime latestReliedMessage = loadViewRelayMessagePort.getLatestReliedMessageBy(id);
                    return DestinationView.of(id.getValue(), destination.getAddress().getValue(), destination.getType(), destination.getStatus(), leg, latestReliedMessage);
                }).collect(Collectors.toUnmodifiableList());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard");
        modelAndView.addObject("destinationViews", destinationViews);
        return modelAndView;
    }
}
