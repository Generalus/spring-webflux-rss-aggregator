package com.thesn.rss.aggregator.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.reactivestreams.client.*;
import com.thesn.rss.aggregator.model.Event;
import org.bson.Document;


public class EventRepository  {


    private static EventRepository eventRepository;

    private MongoClient mongoClient = MongoClients.create();

    private MongoDatabase database = mongoClient.getDatabase("test");

    private MongoCollection<Document> collection = database.getCollection("testCollection",Document.class);


    public static EventRepository getInstance() {
        if(eventRepository==null){
            eventRepository = new EventRepository();
        }
        return eventRepository;
    }

    private EventRepository() {

    }


    public void save(Event event) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(event);

            SubscriberHelpers.OperationSubscriber subscriber = new SubscriberHelpers.OperationSubscriber<Success>();
            collection.insertOne(Document.parse(json))
                .subscribe(subscriber);

            subscriber.await();
        } catch (Throwable throwable) {
            throwable.printStackTrace();    
        }
    }


}
