package co.com.ajac.infrastructure.api.processors;

import co.com.ajac.base.errors.AppError;
import co.com.ajac.concurrency.FutureEither;
import co.com.ajac.infrastructure.api.commands.Command;
import co.com.ajac.infrastructure.api.commands.CommandError;
import co.com.ajac.infrastructure.api.commands.Request;
import co.com.ajac.infrastructure.api.controllers.ControllerProvider;
import co.com.ajac.infrastructure.api.controllers.ControllerType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.apache.commons.lang3.StringUtils;

sealed interface ProcessorUtil permits Processor {

    default List<String> splitUrlPath(String path) {
        return List.of(StringUtils.split(path, '/'));
    }

    default Option<ControllerType> findControllerType(List<String> tagsUtl) {
        return tagsUtl.headOption()
          .map(ControllerType::get);
    }

    default FutureEither<AppError, ControllerProvider> findController(Option<String> pathOpt, List<ControllerProvider> controllerProviders) {
        return FutureEither.fromEither(pathOpt
          .map(this::splitUrlPath)
          .flatMap(this::findControllerType)
          .flatMap(controllerType -> controllerProviders
            .find(controllerProvider -> controllerProvider.getType().equals(controllerType)))
          .toEither(CommandError.COMMAND_PROVIDER_NOT_FOUND)
        );
    }

    default FutureEither<AppError, Command> findCommand(Option<String> commandNameOpt, ControllerProvider controllerProvider) {
        return FutureEither.fromEither(commandNameOpt
          .flatMap(controllerProvider::provide)
          .toEither(CommandError.COMMAND_NOT_IMPLEMENTED)
        );
    }

    default FutureEither<AppError, Request> findRequest(Option<String> commandNameOpt, Option<JsonNode> commandBodyOpt, ControllerProvider controllerProvider) {
        return FutureEither.fromEither(commandNameOpt
          .toEither((AppError) CommandError.COMMAND_NOT_FOUND)
          .flatMap(commandName ->
            commandBodyOpt
              .toEither((AppError) CommandError.COMMAND_REQUEST_NOT_FOUND)
              .map(commandBody -> controllerProvider.deserialize(commandBody, commandName))
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

