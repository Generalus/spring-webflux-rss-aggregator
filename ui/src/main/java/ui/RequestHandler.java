package ui;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import streamGenerator.Event;
import streamGenerator.FluxKind;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class RequestHandler {

    public static Mono<ServerResponse> handleRootRequest(final ServerRequest request) {
        return ok()
                .contentType(TEXT_HTML)
                .body(
                        Mono.just(ResourceSupplier.getResource("index.html")),
                        String.class
                );
    }

    public static Mono<ServerResponse> handleEventStreamRequest(final ServerRequest request) {
        return ok()
                .contentType(TEXT_EVENT_STREAM)
                .body(
                        Flux.just(new Event())  // handshake with a client
                                .concatWith(Flux.merge(
                                        Flux.fromArray(FluxKind.values()).map(FluxKind::connect)
                                ))
                                .distinct(),
                        Event.class
                );
    }

    private RequestHandler() {
    }

}