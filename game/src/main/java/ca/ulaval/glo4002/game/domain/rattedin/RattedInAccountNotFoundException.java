package ca.ulaval.glo4002.game.domain.rattedin;

public class RattedInAccountNotFoundException extends RuntimeException {
    public RattedInAccountNotFoundException() {
        super("Character not found.");
    }
}
