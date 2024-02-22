package ca.ulaval.glo4002.game.domain.character;

public class CharacterNotFoundException extends RuntimeException {
    public CharacterNotFoundException() {
        super("Character not found.");
    }
}
