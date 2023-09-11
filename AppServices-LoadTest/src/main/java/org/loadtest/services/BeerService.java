package org.loadtest.services;


import com.couchbase.lite.*;
import org.loadtest.entity.Beer;
import org.loadtest.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class BeerService {

    @Autowired
    BeerRepository beerRepository;

    @Async
    public CompletableFuture<String> createBeer(Beer beer)  {
        String beerId = "";
        try {
            beerId = beerRepository.createBeer(beer);
        } catch (CouchbaseLiteException e) {
            Database.log.getConsole().log(LogLevel.ERROR, LogDomain.DATABASE, "Exception occured while creating beer \n" + beerId);
        }
        return CompletableFuture.completedFuture(beerId);
    }
}
