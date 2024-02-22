package ca.ulaval.glo4002.game.domain.character.factory;

import ca.ulaval.glo4002.game.domain.character.CharacterID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CharacterIDFactoryTest {

    private static final String USERNAME = "Bob";
    private static final CharacterID USER_ID = new CharacterID(USERNAME);

    @Test
    public void whenCreateCharacterID_thenCharacterIDIsCreated() {
        CharacterIDFactory characterIDFactory = new CharacterIDFactory();

        CharacterID characterID = characterIDFactory.create(USERNAME);

        assertEquals(USER_ID, characterID);
    }
}
