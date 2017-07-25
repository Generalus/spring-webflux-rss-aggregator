package com.thesn.rss.aggregator.server;

import org.apache.catalina.LifecycleException;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.ipc.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


public class FunctionalWebApplication {

    public static RouterFunction getRouter() {
        return route(GET("/"), RequestHandler::handleRootRequest)
                .andRoute(GET("/service/"), RequestHandler::handleEventStreamRequest);

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
