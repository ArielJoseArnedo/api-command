package co.com.ajac.infrastructure.api.commands;

import co.com.ajac.base.errors.AppError;
import co.com.ajac.base.events.Event;
import co.com.ajac.concurrency.FutureEither;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;

public interface Command <R extends Response, T extends Request> {
    default FutureEither<AppError, Tuple2<Option<R>, List<Event>>> execute(T request) { return FutureEither.left(CommandError.COMMAND_NOT_IMPLEMENTED); }
}
