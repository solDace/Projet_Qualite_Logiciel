package ca.ulaval.glo4002.game.domain.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterInteractionGameActionFactoryTest {

    private static final int GAME_TURN = 5;
    private static final String SENDING_CHARACTER = "Bobino";
    private static final String RECEIVING_CHARACTER = "Bobinette";
    private static final CharacterInteractionActionType ACTION_TYPE = CharacterInteractionActionType.GOSSIP;

    private CharacterInteractionGameActionFactory actionFactory;

    @BeforeEach
    public void setUp() {
        actionFactory = new CharacterInteractionGameActionFactory();
    }

    @Test
    public void givenParameters_whenCreateCharacterInteractionGameAction_thenCreateActionWithThoseParameters() {

        CharacterInteractionGameAction action =
            actionFactory.createCharacterInteractionGameAction(SENDING_CHARACTER, RECEIVING_CHARACTER, ACTION_TYPE, GAME_TURN);

        assertEquals(ACTION_TYPE, action.characterInteractionActionType());
        assertEquals(SENDING_CHARACTER, action.senderID());
        assertEquals(RECEIVING_CHARACTER, action.receiverID());
        assertEquals(GAME_TURN, action.turnNumber());
    }
}
