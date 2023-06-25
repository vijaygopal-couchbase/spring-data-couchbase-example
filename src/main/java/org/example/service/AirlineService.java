package org.example.service;

import org.example.entity.Airline;
import org.example.repository.AirlineRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirlineService {

    private final AirlineRepository airlineRepostitory;

    public AirlineService(AirlineRepository airlineRepostitory){
        this.airlineRepostitory = airlineRepostitory;
    }

    public Optional<Airline> getAirlineDocument(String id){
        return airlineRepostitory.findById(id);
    }
}
