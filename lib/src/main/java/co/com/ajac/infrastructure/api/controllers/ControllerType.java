package co.com.ajac.infrastructure.api.controllers;

import io.vavr.collection.List;

public enum ControllerType {
    UPDATER,
    DELETER,
    REGISTER,
    UNKNOWN;

    public static ControllerType get(String name) {
        return List.of(ControllerType.values())
          .find(controllerType -> controllerType.name().equalsIgnoreCase(name))
          .getOrElse(UNKNOWN);
    }
}
