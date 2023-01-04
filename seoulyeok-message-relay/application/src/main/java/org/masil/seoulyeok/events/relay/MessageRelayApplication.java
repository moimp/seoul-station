package org.masil.seoulyeok.events.relay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MessageRelayApplication {

    @RestController
    public static class HelloController {
        @GetMapping(value = {"/annyeng"})
        public String hello() {
            return "hello";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(MessageRelayApplication.class, args);
    }
}
