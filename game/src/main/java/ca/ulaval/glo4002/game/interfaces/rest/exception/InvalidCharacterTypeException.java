package ca.ulaval.glo4002.game.interfaces.rest.exception;

public class InvalidCharacterTypeException extends InvalidParameterException {
    public InvalidCharacterTypeException() {
        super("Invalid Character Type");
    }

    @Override
    public String getError() {
        return "INVALID_TYPE";
    }

    @Override
    public String getDescription() {
        return "Invalid type.";
    }
}
