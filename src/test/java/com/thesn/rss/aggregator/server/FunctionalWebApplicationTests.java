package com.thesn.rss.aggregator.server;

import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

public class FunctionalWebApplicationTests {

    private final WebTestClient webTestClient =
            WebTestClient.bindToRouterFunction(FunctionalWebApplication.getRouter()).build();

    @Test
    public void index_html_on_index_page() {
        webTestClient.get().uri("/").exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo(ResourceSupplier.getResource("index.html"));
    }

    @Test
    public void event_stream_on_service_page() {

        webTestClient.get().uri("/service/").exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(TEXT_EVENT_STREAM);

    }

}
