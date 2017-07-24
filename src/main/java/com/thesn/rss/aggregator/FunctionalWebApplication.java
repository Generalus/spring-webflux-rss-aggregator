package com.thesn.rss.aggregator;

import org.apache.catalina.LifecycleException;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

import java.io.IOException;


import static com.thesn.rss.aggregator.generators.FluxGeneratorFactory.generateEventFlux;
import static com.thesn.rss.aggregator.generators.FluxName.*;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


public class FunctionalWebApplication {

    static RouterFunction getRouter() {
        return
                route(
                        GET("/"), request ->
                                ok().contentType(TEXT_HTML)
                                        .body(
                                                Mono.just(ResourceSupplier.getResource("index.html")),
                                                String.class
                                        )
                )
                .andRoute(
                        GET("/service/"), req ->
                                ok().contentType(TEXT_EVENT_STREAM)
                                    .body(
                                            Flux.just(new Event())  // даем клиенту понять, что соединение установлено
                                                    .mergeWith(generateEventFlux(JAVA))
                                                    .mergeWith(generateEventFlux(JAVASCRIPT))
                                                    .mergeWith(generateEventFlux(PYTHON))
                                                    .distinct(),
                                            Event.class
                                    )

                );

    }

    public static void main(String[] args) throws IOException, LifecycleException, InterruptedException {

        HttpHandler httpHandler = RouterFunctions.toHttpHandler(getRouter());

        HttpServer
                .create("localhost", 8080)
                .newHandler(new ReactorHttpHandlerAdapter(httpHandler))
                .block();

        generateEventFlux(JAVA);
        generateEventFlux(JAVASCRIPT);
        generateEventFlux(PYTHON);

        Thread.currentThread().join();
    }
}
