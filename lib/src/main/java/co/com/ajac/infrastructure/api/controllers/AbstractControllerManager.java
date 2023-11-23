package co.com.ajac.infrastructure.api.controllers;

import io.vavr.collection.List;

public interface AbstractControllerManager {
    List<ControllerProvider> getControllerProviders();
}
