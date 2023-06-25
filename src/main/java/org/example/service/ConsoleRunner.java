package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final AirlineService airlineService;

    @Autowired
    public ConsoleRunner(final AirlineService airlineService) {
        this.airlineService = airlineService;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println(airlineService.getAirlineDocument("airline_10"));
    }
}
