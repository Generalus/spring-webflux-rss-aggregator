package com.thesn.rss.aggregator.generators;

import com.thesn.rss.aggregator.Event;
import reactor.core.publisher.Flux;

class JavaFluxGenerator extends AbstractFluxGenerator {
    @Override
    public Flux<Event> generateFlux() {
        return generateCommonFlux("https://stackoverflow.com/feeds/tag?tagnames=java&sort=newest", "Java");
    }
}
