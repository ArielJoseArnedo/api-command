package co.com.ajac.infrastructure.api.commands;

import io.vavr.collection.List;

public interface ProviderManager {
    List<CommandProvider> getCommandProviders();
}
