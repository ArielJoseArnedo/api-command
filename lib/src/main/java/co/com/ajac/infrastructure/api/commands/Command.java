package co.com.ajac.infrastructure.api.commands;

import co.com.ajac.concurrency.FutureEither;
import co.com.ajac.domain.errors.AppError;
import co.com.ajac.infrastructure.api.events.Event;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;


public interface Command{
    default FutureEither<AppError, Tuple2<Option<Response>, List<Event>>> execute(Request request) { return FutureEither.left(CommandError.COMMAND_NOT_IMPLEMENT); }
}
