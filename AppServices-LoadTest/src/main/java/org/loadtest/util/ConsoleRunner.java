package org.loadtest.util;

import org.loadtest.entity.Beer;
import org.loadtest.services.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private  BeerService beerService;

    @Autowired
    public ConsoleRunner(BeerService beerService) {
        this.beerService = beerService;
    }

    @Override
    public void run(String... args) throws Exception {

        Map<String,String> argsMap = argsMap(args);
        if(!argsMap.isEmpty()) {
            DbUtils.startReplication();
            invokeBeerCreation(Integer.parseInt(argsMap.get("numThreads")));
        }
    }

    private void invokeBeerCreation(int numThreads) throws InterruptedException, ExecutionException {
        CompletableFuture<String> completedFuture[] = new CompletableFuture[numThreads];

        for(int i = numThreads; i>0; i--){
            for(int j = 0; j < 300; j++){
            completedFuture[i-1] = beerService.createBeer(createBeer());
            }
        }
        CompletableFuture.allOf(completedFuture).join();
    }

    public Map<String,String> argsMap(String[] args){
        Map<String,String> resultMap = new HashMap<String,String>();
        List.of(args).forEach(item->{
            List.of(item.split(",")).stream().forEach(var->{
                String kvPair[] = var.split("=");
                resultMap.put(kvPair[0],kvPair[1]);
            });
        });
        return resultMap;
    }

    private Beer createBeer(){
        Beer beer = Beer.builder()
                .abv("5")
                .name("563 Stout")
                .srm("5")
                .upc("10")
                .style("American-Style Stout")
                .ibv("6")
                .type("beer")
                .brewery_id("brewery_id")
                .updated("2010-07-22 20:00:20")
                .description("Deep black color, toasted black burnt coffee flavors and aroma. Dispensed with Nitrogen through a slow-flow faucet giving it the characteristic cascading effect, resulting in a rich dense creamy head.")
                .build();
        return beer;
    }
}

