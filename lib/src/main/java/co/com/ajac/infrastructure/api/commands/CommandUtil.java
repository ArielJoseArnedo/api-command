package co.com.ajac.infrastructure.api.commands;

import co.com.ajac.concurrency.FutureEither;
import co.com.ajac.domain.errors.AppError;
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

    default FutureEither<AppError, Command> findCommand(Option<CommandName> commandNameOpt, CommandProvider commandProvider) {
        return FutureEither.fromEither(commandNameOpt
          .flatMap(commandName -> commandProvider.provide(commandName.getName()))
          .toEither(CommandError.COMMAND_NOT_IMPLEMENTED)
        );
    }
}

