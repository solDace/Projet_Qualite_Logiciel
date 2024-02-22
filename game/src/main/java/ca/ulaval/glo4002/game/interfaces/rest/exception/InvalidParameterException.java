package ca.ulaval.glo4002.game.interfaces.rest.exception;

import jakarta.validation.ValidationException;

public abstract class InvalidParameterException extends ValidationException {

    public InvalidParameterException(String message) {
        super(message);
    }

    public abstract String getError();

    public abstract String getDescription();
}
