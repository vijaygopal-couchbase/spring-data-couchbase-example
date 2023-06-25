package org.example.repository;

import org.example.entity.Airline;
import org.springframework.data.repository.CrudRepository;

public interface AirlineRepository extends CrudRepository<Airline,String> {
}
