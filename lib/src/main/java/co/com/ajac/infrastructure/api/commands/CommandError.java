package co.com.ajac.infrastructure.api.commands;

import co.com.ajac.domain.errors.AppError;

public enum CommandError implements AppError {
    COMMAND_NOT_IMPLEMENTED("EIC-1", "Command not implmented", "Command has no implementation, so it cannot be executed.");

    private final String code;
    private final String message;
    private final String description;

    CommandError(String code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public String description() {
        return description;
    }
}