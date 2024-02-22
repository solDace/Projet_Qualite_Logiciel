package ca.ulaval.glo4002.game.interfaces.converter;

import ca.ulaval.glo4002.game.domain.character.CharacterType;
import ca.ulaval.glo4002.game.interfaces.rest.dto.character.RodentType;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidCharacterTypeException;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidParameterException;

public class RodentToCharacterTypeConverter {

    public static RodentType convertToRodentType(CharacterType characterType) throws InvalidParameterException {
        RodentType rodentType;

        switch (characterType) {
            case ACTOR -> {
                rodentType = RodentType.hamster;
            }
            case AGENT -> {
                rodentType = RodentType.chinchilla;
            }
            case LAWYER -> {
                rodentType = RodentType.rat;
            }
            default -> {
                throw new InvalidCharacterTypeException();
            }
        }
        return rodentType;
    }

    public static CharacterType convertToCharacterType(RodentType rodentType) throws InvalidCharacterTypeException {
        CharacterType characterType;

        switch (rodentType) {
            case hamster -> {
                characterType = CharacterType.ACTOR;
            }
            case chinchilla -> {
                characterType = CharacterType.AGENT;
            }
            case rat -> {
                characterType = CharacterType.LAWYER;
            }
            default -> {
                throw new InvalidCharacterTypeException();
            }
        }
        return characterType;
    }
}
