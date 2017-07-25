package com.thesn.rss.aggregator.model;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.LoggerFactory;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.net.URL;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

public enum FluxKind {

    JAVA("https://stackoverflow.com/feeds/tag?tagnames=java&sort=newest", "Java"),
    JAVASCRIPT("https://stackoverflow.com/feeds/tag?tagnames=javascript&sort=newest", "Javascript"),
    PYTHON("https://stackoverflow.com/feeds/tag?tagnames=python&sort=newest", "Python");

    String rssUrl;

    String rssName;

    Flux<Event> cachedFlux;


    public Flux<Event> connect() {
        return cachedFlux;
    }

    FluxKind(String url, String name) {
        this.rssUrl = url;
        this.rssName = name;
        this.cachedFlux = createFlux();

        LoggerFactory.getLogger("main").warn(rssName + "flux was created!");
    }

    private Flux<Event> createFlux(){
        return Flux.generate(FluxState::new, this::generate)
                .map(Event.class::cast)
                .delayElements(Duration.ofSeconds(5))
                .doOnNext(System.out::println) // TODO put in a database instead
                .share();
    }

    private FluxState generate(final FluxState state, final SynchronousSink<Object> sink) {
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
                LoggerFactory.getLogger("main").warn("Network Request");
            }

            sink.next(eventsStack.isEmpty() ? new Event() : eventsStack.pollLast());

        } catch (Exception e) {
            Exceptions.propagate(e);
        }
        return state;
    }
}
