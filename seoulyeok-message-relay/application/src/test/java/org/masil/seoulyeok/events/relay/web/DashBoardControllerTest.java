package org.masil.seoulyeok.events.relay.web;

import org.masil.seoulyeok.events.destination.Address;
import org.masil.seoulyeok.events.destination.Destination;
import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.relay.port.out.LoadViewDestinationPort;
import org.masil.seoulyeok.events.relay.port.out.LoadViewRelayMessagePort;
import org.masil.seoulyeok.events.relay.web.model.DestinationView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.masil.seoulyeok.events.destination.DestinationStatus.ACTIVE;
import static org.masil.seoulyeok.events.destination.DestinationType.SIMPLE_QUEUE;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = DashBoardController.class)
class DashBoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LoadViewDestinationPort destinationPort;

    @MockBean
    LoadViewRelayMessagePort loadViewRelayMessagePort;
    private static final DestinationId ANY_DESTINATION_ID = DestinationId.of(1L);
    private static final LocalDateTime ANY_LOCALDATETIME = LocalDateTime.of(2022, 1, 1, 1, 1);
    private static final Address ANY_ADDRESS = Address.of("address");

    @Test
    void destinationView() throws Exception {
        List<Destination> destinations = new ArrayList<>();
        destinations.add(Destination.of(ANY_DESTINATION_ID, ANY_ADDRESS, SIMPLE_QUEUE));

        List<DestinationView> destinationViews = new ArrayList<>();

        destinationViews.add(DestinationView.of(1L, "address", SIMPLE_QUEUE, ACTIVE, 10L, ANY_LOCALDATETIME));

        given(destinationPort.findAllDestination()).willReturn(destinations);
        given(loadViewRelayMessagePort.getLatestReliedMessageBy(ANY_DESTINATION_ID)).willReturn(ANY_LOCALDATETIME);
        given(loadViewRelayMessagePort.getLegByDestinationId(ANY_DESTINATION_ID)).willReturn(10L);

        mockMvc.perform(get("/dashboard"))
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("destinationViews", destinationViews))
                .andReturn();
    }


}
