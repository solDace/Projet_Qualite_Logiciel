package ca.ulaval.glo4002.game.interfaces.converter;

import ca.ulaval.glo4002.game.domain.character.CharacterType;
import ca.ulaval.glo4002.game.interfaces.rest.dto.character.RodentType;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RodentToCharacterTypeConverterTest {

    @Test
    public void givenAnChinchilla_convertToCharacterType_thenReturnAnAgent() {
        RodentType rodentType = RodentType.chinchilla;

        CharacterType characterType = RodentToCharacterTypeConverter.convertToCharacterType(rodentType);

        assertEquals(CharacterType.AGENT, characterType);
    }

    @Test
    public void givenARat_convertToCharacterType_thenReturnALawyer() {
        RodentType rodentType = RodentType.rat;

        CharacterType characterType = RodentToCharacterTypeConverter.convertToCharacterType(rodentType);

        assertEquals(CharacterType.LAWYER, characterType);
    }

    @Test
    public void givenAHamster_convertToCharacterType_thenReturnAnActor() {
        RodentType rodentType = RodentType.hamster;

        CharacterType characterType = RodentToCharacterTypeConverter.convertToCharacterType(rodentType);

        assertEquals(CharacterType.ACTOR, characterType);
    }

    @Test
    public void givenAnActor_convertToRodentType_thenReturnAHamster() {
        CharacterType characterType = CharacterType.ACTOR;

        RodentType rodentType = RodentToCharacterTypeConverter.convertToRodentType(characterType);

        assertEquals(RodentType.hamster, rodentType);
    }

    @Test
    public void givenAnAgent_convertToRodentType_thenReturnAChinchilla() {
        CharacterType characterType = CharacterType.AGENT;

        RodentType rodentType = RodentToCharacterTypeConverter.convertToRodentType(characterType);

        assertEquals(RodentType.chinchilla, rodentType);
    }

    @Test
    public void givenALawyer_convertToRodentType_thenReturnARat() {
        CharacterType characterType = CharacterType.LAWYER;

        RodentType rodentType = RodentToCharacterTypeConverter.convertToRodentType(characterType);

        assertEquals(RodentType.rat, rodentType);
    }
}
