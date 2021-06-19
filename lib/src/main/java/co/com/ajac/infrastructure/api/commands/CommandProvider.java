package co.com.ajac.infrastructure.api.commands;

import com.fasterxml.jackson.databind.JsonNode;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.Getter;

public abstract class CommandProvider {

    @Getter protected String version;
    protected Map<CommandName, Tuple2<Command, Request>> commands;

    protected abstract Option<Command> provide(String commandName);

    protected abstract Try<Request> deserialize(JsonNode command, String commandName);
}
