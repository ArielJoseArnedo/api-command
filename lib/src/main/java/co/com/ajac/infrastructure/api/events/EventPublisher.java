package co.com.ajac.infrastructure.api.events;


import io.vavr.collection.List;

public interface EventPublisher {

    List<Event> publish(List<Event> events);
}
