package com.thesn.search.aggregator;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

public class FunctionalWebApplicationTests {

    private final WebTestClient webTestClient =
            WebTestClient.bindToRouterFunction(FunctionalWebApplication.getRouter()).build();

    @Test
    public void indexPage_WhenRequested_SaysHello() {
        webTestClient.get().uri("/").exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo(HtmlSupplier.getHtml("index.html"));
    }

    @Ignore
    public void jsonPage_WhenRequested_SaysHello() {
        webTestClient.get().uri("/json").exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(APPLICATION_STREAM_JSON)
                .expectBodyList(Event.class)
                .isEqualTo(
                        IntStream.range(0, 100)
                                .boxed()
                                .map(String::valueOf)
                                .map(Event::new)
                                .collect(Collectors.toList())
                );
    }

}
