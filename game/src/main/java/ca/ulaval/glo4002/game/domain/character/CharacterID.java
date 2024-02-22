package ca.ulaval.glo4002.game.domain.character;

public class CharacterID {
    private final String characterName;

    public CharacterID(String characterName) {
        this.characterName = characterName;
    }

    public String asString() {
        return characterName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CharacterID otherCharacterID)) {
            return false;
        }

        return otherCharacterID.characterName.equals(characterName);
    }

    @Override
    public int hashCode() {
        return characterName.hashCode();
    }
}
