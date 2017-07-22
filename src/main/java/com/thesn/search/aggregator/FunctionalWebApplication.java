package com.thesn.search.aggregator;

import org.apache.catalina.LifecycleException;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

import java.io.IOException;
import java.time.Duration;

import static com.thesn.search.aggregator.HtmlSupplier.getHtml;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;



public class FunctionalWebApplication {

    static RouterFunction getRouter() {
        HandlerFunction hello = request -> ok().contentType(TEXT_HTML).body(Mono.just(getHtml("index.html")), String.class);

        return
            route(
                GET("/"), hello)
            .andRoute(
                GET("/json"), req -> ok().contentType(TEXT_EVENT_STREAM).body(Flux.range(0, 100).map(String::valueOf).map(Hello::new).delayElements(Duration.ofSeconds(2)), Hello.class));
    }

    public static void main(String[] args) throws IOException, LifecycleException, InterruptedException {

        HttpHandler httpHandler = RouterFunctions.toHttpHandler(getRouter());

        HttpServer
                .create("localhost", 8080)
                .newHandler(new ReactorHttpHandlerAdapter(httpHandler))
                .block();

        Thread.currentThread().join();
    }
}
