package ca.ulaval.glo4002.game.interfaces.rest.exception;

public class InvalidActionCodeException extends InvalidParameterException {
    public InvalidActionCodeException() {
        super("Invalid Action Code");
    }

    @Override
    public String getError() {
        return "INVALID_ACTION_CODE";
    }

    @Override
    public String getDescription() {
        return "The action code is invalid.";
    }
}
