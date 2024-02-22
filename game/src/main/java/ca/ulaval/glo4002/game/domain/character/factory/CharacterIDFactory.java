package ca.ulaval.glo4002.game.domain.character.factory;

import ca.ulaval.glo4002.game.domain.character.CharacterID;

public class CharacterIDFactory {

    public CharacterID create(String characterName) {
        return new CharacterID(characterName);
    }
}
