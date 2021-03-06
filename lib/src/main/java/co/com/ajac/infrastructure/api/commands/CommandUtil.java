package co.com.ajac.infrastructure.api.commands;

import co.com.ajac.base.errors.AppError;
import co.com.ajac.concurrency.FutureEither;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.apache.commons.lang3.StringUtils;

public interface CommandUtil {

    default List<String> splitUrlPath(String path) {
        return List.of(StringUtils.split(path, '/'));
    }

    default Option<String> findVersionFromTagsUrl(List<String> tagsUtl) {
        return tagsUtl.headOption();
    }

    default FutureEither<AppError, CommandProvider> findProvider(Option<String> pathOpt, List<CommandProvider> commandProviders) {
        return FutureEither.fromEither(pathOpt
          .map(this::splitUrlPath)
          .flatMap(this::findVersionFromTagsUrl)
          .flatMap(tagVersion -> commandProviders.find(commandProvider -> commandProvider.getVersion().equals(tagVersion)))
          .toEither(CommandError.COMMAND_PROVIDER_NOT_FOUND)
        );
    }

    default FutureEither<AppError, Command> findCommand(Option<String> commandNameOpt, CommandProvider commandProvider) {
        return FutureEither.fromEither(commandNameOpt
          .flatMap(commandProvider::provide)
          .toEither(CommandError.COMMAND_NOT_IMPLEMENTED)
        );
    }

    default FutureEither<AppError, Request> findRequest(Option<String> commandNameOpt, Option<JsonNode> commandBodyOpt, CommandProvider commandProvider) {
        return FutureEither.fromEither(commandNameOpt
          .toEither((AppError) CommandError.COMMAND_NOT_FOUND)
          .flatMap(commandName ->
            commandBodyOpt
              .toEither((AppError) CommandError.COMMAND_REQUEST_NOT_FOUND)
              .map(commandBody -> commandProvider.deserialize(commandBody, commandName))
          )
          .flatMap(tryRequest -> tryRequest.toEither(CommandError.COMMAND_REQUEST_NOT_DESERIALIZED))
        );
    }

    default JsonNode makeResponseError(AppError appError) {
        final JsonNodeFactory factory = JsonNodeFactory.instance;
        return factory.objectNode()
          .put("code", appError.code())
          .put("causal", appError.message())
          .put("description", appError.description());
    }
}

