package ca.ulaval.glo4002.game.interfaces.rest.exception;

public class InvalidCharacterNameException extends InvalidParameterException {
    public InvalidCharacterNameException() {
        super("Invalid Character Name");
    }

    @Override
    public String getError() {
        return "INVALID_NAME";
    }

    @Override
    public String getDescription() {
        return "Invalid name.";
    }
}
