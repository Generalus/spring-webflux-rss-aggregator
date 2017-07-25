package com.thesn.rss.aggregator;

import com.thesn.rss.aggregator.server.FunctionalWebApplication;
import com.thesn.rss.aggregator.server.ResourceSupplier;
import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

public class FunctionalWebApplicationTests {

    private final WebTestClient webTestClient =
            WebTestClient.bindToRouterFunction(FunctionalWebApplication.getRouter()).build();

    @Test
    public void indexPage_WhenRequested_SaysHello() {
        webTestClient.get().uri("/").exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo(ResourceSupplier.getResource("index.html"));
    }

}
