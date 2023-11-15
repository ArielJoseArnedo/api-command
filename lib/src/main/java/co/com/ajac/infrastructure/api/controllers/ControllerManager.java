package co.com.ajac.infrastructure.api.controllers;

import io.vavr.collection.List;

public interface ControllerManager {
    List<ControllerProvider> getControllerProviders();
}
