package com.thesn.rss.aggregator.generators;

import com.thesn.rss.aggregator.Event;
import reactor.core.publisher.Flux;

public interface FluxGenerator {
    Flux<Event> generateFlux();
}
