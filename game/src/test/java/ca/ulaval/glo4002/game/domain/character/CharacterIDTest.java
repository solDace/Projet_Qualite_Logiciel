package ca.ulaval.glo4002.game.domain.character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CharacterIDTest {

    private static final String CHARACTER_NAME = "Bob", ANOTHER_CHARACTER_NAME = "Alice";

    private CharacterID characterID;

    @BeforeEach
    public void setup() {
        characterID = new CharacterID(CHARACTER_NAME);
    }

    @Test
    public void givenACharacterIdCreatedWithAString_whenAsString_thenTheStringIsReturned() {

        String name = characterID.asString();

        assertEquals(CHARACTER_NAME, name);
    }

    @Test
    public void givenTwoCharacterIDCreatedWithSameString_whenComparing_thenReturnTrue() {
        CharacterID anIdenticalCharacterId = new CharacterID(CHARACTER_NAME);

        boolean comparison = characterID.equals(anIdenticalCharacterId);

        assertTrue(comparison);
    }

    @Test
    public void givenTwoCharacterIDsCreatedWithDifferentString_whenEquals_thenReturnFalse() {
        CharacterID aDifferentCharacterId = new CharacterID(ANOTHER_CHARACTER_NAME);

        boolean comparison = characterID.equals(aDifferentCharacterId);

        assertFalse(comparison);
    }
}
