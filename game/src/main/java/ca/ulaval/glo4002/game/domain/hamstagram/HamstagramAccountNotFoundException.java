package ca.ulaval.glo4002.game.domain.hamstagram;

public class HamstagramAccountNotFoundException extends RuntimeException {
    public HamstagramAccountNotFoundException() {
        super("Character not found.");
    }
}
