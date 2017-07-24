# spring-webflux-rss-aggregator

This is a simple RSS-grabber (main and single page will be available in localhost:8080/ after deployment, it uses event-streaming /service/ ).
Application registers three topics from StackOverflow (Java, Javascript and Python) and send the latest content to the webpage's visitors. RSS is updated slowly on the StackOverflow side, so, some data will be available at least in 2-3 minutes.

Technology stack: Spring Boot, Spring Web Flux, Reactor Core, Event sourcing, Rome.

Todo: keeping all entries in Mongo DB.

# Screenshots

### Some data has been already loaded
![](http://s019.radikal.ru/i628/1707/41/11d1e6916c91.png)

### Downloading is in progress, but no data is present
![](http://s018.radikal.ru/i518/1707/de/75118a6b3115.png)

### Connection was lost
![](http://s019.radikal.ru/i601/1707/55/080d085b432a.png)
