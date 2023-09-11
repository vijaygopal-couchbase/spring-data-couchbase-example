package org.loadtest.repository;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.MutableDocument;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.loadtest.entity.Beer;
import org.loadtest.util.DbUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@EqualsAndHashCode
@AllArgsConstructor
public class BeerRepository {

    private final Database database = DbUtils.getDatabase();

    public  String createBeer(Beer beer) throws CouchbaseLiteException {
        String beerId = UUID.randomUUID().toString().replace("-", "");
        MutableDocument document = new MutableDocument(beerId);
        mapBeerDetails(beer, document);
        database.save(document);
        return beerId;
    }

    private void mapBeerDetails(Beer beer,  MutableDocument document) {
        document.setString("name", beer.getName());
        document.setString("abv", beer.getAbv());
        document.setString("ibv",  beer.getIbv());
        document.setString("srm",  beer.getSrm());
        document.setString("category",  beer.getCategory());
        document.setString("brewery_id", beer.getBrewery_id());
        document.setString("type", "beer");
        document.setString("updated", beer.getUpdated());
        document.setString("description", beer.getDescription());
        document.setString("style",beer.getStyle());
        document.setString("category",beer.getCategory());
    }
}
