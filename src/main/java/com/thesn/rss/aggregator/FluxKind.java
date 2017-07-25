package com.thesn.rss.aggregator;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

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


    public Flux<Event> get() {
        return cachedFlux;
    }

    FluxKind(String url, String name) {
        this.rssUrl = url;
        this.rssName = name;
        this.cachedFlux = generateFlux();
        LoggerFactory.getLogger("main").info(rssName + "flux was created!");
    }

    private Flux<Event> generateFlux(){
        return Flux.generate(
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

                        sink.next(eventsStack.isEmpty() ? new Event() : eventsStack.pollLast());

                    } catch (Exception e) {
                        Exceptions.propagate(e);
                    }
                    return state;
                }

        ).map(Event.class::cast).delayElements(Duration.ofSeconds(5))
                .doOnNext(System.out::println).share();


    }

}
