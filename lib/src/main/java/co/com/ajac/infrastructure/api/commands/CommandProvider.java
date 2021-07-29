package co.com.ajac.infrastructure.api.commands;

import com.fasterxml.jackson.databind.JsonNode;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import io.vavr.control.Try;

public abstract class CommandProvider {

    protected String version;
    protected Map<CommandName, Tuple2<Command, Request>> commands;

    public abstract Option<Command> provide(String commandName);

    public abstract Try<Request> deserialize(JsonNode command, String commandName);

    public String getVersion() {
        return version;
    }
}
