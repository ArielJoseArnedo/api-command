package co.com.ajac.infrastructure.api.commands;

import io.vavr.control.Option;

public interface CommandName<SubClass extends CommandName<SubClass>> {
    String getName();
    Option<SubClass> getCommandName(String name);
}
