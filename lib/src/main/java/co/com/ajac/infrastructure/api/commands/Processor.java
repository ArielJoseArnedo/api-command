package co.com.ajac.infrastructure.api.commands;

import co.com.ajac.infrastructure.api.events.EventPublisher;

import java.util.List;

public interface Processor {
    List<CommandProvider> commandProviders();
    EventPublisher eventPublisher();
}
