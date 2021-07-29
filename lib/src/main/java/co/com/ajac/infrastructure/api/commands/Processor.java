package co.com.ajac.infrastructure.api.commands;


import co.com.ajac.base.events.Publisher;
import io.vavr.collection.List;

public interface Processor {
    List<CommandProvider> commandProviders();
    Publisher publisher();
}
