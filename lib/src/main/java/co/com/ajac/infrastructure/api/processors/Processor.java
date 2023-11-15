package co.com.ajac.infrastructure.api.processors;


import co.com.ajac.infrastructure.api.controllers.ControllerProvider;
import co.com.ajac.messaging.publishers.PublisherProvider;
import io.vavr.collection.List;

public non-sealed interface Processor extends ProcessorUtil {
    List<ControllerProvider> commandProviders();
    PublisherProvider publisher();
}
