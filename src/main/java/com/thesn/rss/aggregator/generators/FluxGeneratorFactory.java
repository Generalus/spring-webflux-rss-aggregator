package com.thesn.rss.aggregator.generators;

import com.thesn.rss.aggregator.Event;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

public final class FluxGeneratorFactory {
    private static final Map<FluxName, FluxGenerator> mapping;

    static {
        mapping = new HashMap<>();
        mapping.put(FluxName.JAVA, new JavaFluxGenerator());
        mapping.put(FluxName.JAVASCRIPT, new JavascriptFluxGenerator());
        mapping.put(FluxName.PYTHON, new PythonFluxGenerator());
    }

    public static Flux<Event> generateEventFlux(FluxName name) {
        return mapping.get(name).generateFlux();
    }

}
