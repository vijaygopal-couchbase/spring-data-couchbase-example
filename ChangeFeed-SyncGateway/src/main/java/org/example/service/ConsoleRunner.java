package org.example.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private static final String URL = "https://rpzfzetjx6threef.apps.cloud.couchbase.com:4984/ag2go/_changes?feed=continuous&since=2";

    private final ChangeFeedConsumer cfc;

    public ConsoleRunner(ChangeFeedConsumer cfc) {
        this.cfc = cfc;
    }

    @Override
    public void run(String... args) throws Exception {
        cfc.consumeStreamingApi(URL);
    }
}
