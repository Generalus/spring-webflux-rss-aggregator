package com.thesn.rss.aggregator.generators;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.thesn.rss.aggregator.Event;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

import java.net.URL;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

abstract class AbstractFluxGenerator implements FluxGenerator {
    private Flux<Event> cachedFlux;

    public Flux<Event> generateCommonFlux(String rssUrl, String rssName) {
        if (cachedFlux == null) {
            cachedFlux = Flux.generate(
                    FluxState::new,
                    (state, sink) -> {
                        try {
                            LinkedList<Event> eventsStack = state.getActualEvents();
                            if (eventsStack.isEmpty()) {
                                URL feedUrl = new URL(rssUrl);

                                List<SyndEntry> rssEntries = new SyndFeedInput()
                                        .build(new XmlReader(feedUrl))
                                        .getEntries();

                                rssEntries
                                        .stream()
                                        .map(e -> new Event(e.getLink(), e.getTitle(), rssName, e.getPublishedDate()))
                                        .filter(e -> e.getDate().compareTo(state.getActualDate()) > 0)
                                        .forEach(eventsStack::add);

                                state.setActualDate(rssEntries.get(0).getPublishedDate());
                                System.err.println("Network Request");
                            }

                            // если горячих событий нет, отсылаем заглушку (пустое событие)
                            if (eventsStack.isEmpty()) {
                                sink.next(new Event());
                            } else {
                                sink.next(eventsStack.pollLast());
                            }
                        } catch (Exception e) {
                            Exceptions.propagate(e);
                            sink.next(new Event());
                        }
                        return state;
                    }

            ).map(Event.class::cast).delayElements(Duration.ofSeconds(5)).delaySubscription(Duration.ofSeconds(30))
                    .doOnNext(System.out::println).share();

            System.err.println(rssName + "flux was created!");
        }

        return cachedFlux;
    }

}
