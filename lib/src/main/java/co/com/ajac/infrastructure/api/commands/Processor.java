package co.com.ajac.infrastructure.api.commands;


import co.com.ajac.messaging.publishers.PublisherProvider;
import io.vavr.collection.List;

public interface Processor {
    List<CommandProvider> commandProviders();
    PublisherProvider publisher();
}
