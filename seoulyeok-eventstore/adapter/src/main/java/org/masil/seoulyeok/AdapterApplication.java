package org.masil.seoulyeok;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AdapterApplication {

    @RestController
    public static class HealthCheckController {
        @GetMapping(value = {"/annyeng"})
        public String hello() {
            return "hello";
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(AdapterApplication.class, args);
    }

}
