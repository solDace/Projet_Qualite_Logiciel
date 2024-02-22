package ca.ulaval.glo4002.game.domain.hamstagram;

import ca.ulaval.glo4002.game.domain.character.CharacterID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HamstagramAccountFactoryTest {

    private static final String USERNAME = "Satori";
    private static final CharacterID CHARACTER_ID = new CharacterID(USERNAME);
    private static final HamstagramID HAMSTAGRAM_ID = new HamstagramID(USERNAME);

    @Mock
    private HamstagramIDFactory hamstagramIDFactory;

    private HamstagramAccountFactory hamstagramAccountFactory;

    @BeforeEach
    public void setUp() {
        hamstagramAccountFactory = new HamstagramAccountFactory(hamstagramIDFactory);
    }

    @Test
    public void givenAnUserNameAndCharacterId_whenCreateAccount_thenHamstagramAccountIsCreated() {
        Mockito.when(hamstagramIDFactory.create(USERNAME)).thenReturn(HAMSTAGRAM_ID);

        HamstagramAccount hamstagramAccount = hamstagramAccountFactory.createAccount(USERNAME, CHARACTER_ID);

        assertEquals(HAMSTAGRAM_ID, hamstagramAccount.getHamstagramID());
        assertEquals(CHARACTER_ID, hamstagramAccount.getCharacterID());
        assertEquals(USERNAME, hamstagramAccount.getUsername());
    }
}
