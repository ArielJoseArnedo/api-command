package co.com.ajac.infrastructure.api.controllers;

import co.com.ajac.infrastructure.api.commands.Command;
import co.com.ajac.infrastructure.api.commands.CommandName;
import co.com.ajac.infrastructure.api.commands.Request;
import com.fasterxml.jackson.databind.JsonNode;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import io.vavr.control.Try;

public abstract class ControllerProvider {

    protected ControllerType type = ControllerType.UNKNOWN;
    protected Map<CommandName, Tuple2<Command, Request>> commands;

    public abstract Option<Command> provide(String commandName);

    public abstract Try<Request> deserialize(JsonNode command, String commandName);

    public ControllerType getType() {
        return type;
    }
}
