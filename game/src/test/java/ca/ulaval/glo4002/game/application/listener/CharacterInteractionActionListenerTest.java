package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.CharacterInteractionService;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionGameAction;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.action.ResetGameGameAction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CharacterInteractionActionListenerTest {

    private static final String CHARACTER_SENDER = "Bob", CHARACTER_RECEIVER = "Charlie";
    private static final CharacterInteractionActionType CHARACTER_TO_CHARACTER_ACTION_TYPE = CharacterInteractionActionType.GOSSIP;
    private static final int A_TURN_NUMBER = 2;
    @Mock
    private CharacterInteractionService characterInteractionService;

    private CharacterInteractionActionListener characterInteractionActionListener;

    @BeforeEach
    public void setUp() {
        characterInteractionActionListener = new CharacterInteractionActionListener(characterInteractionService);
    }

    @Test
    public void givenCharacterInteractionAction_whenOnGameAction_thenCharacterInteractionActionServiceAddsTheAction() {
        CharacterInteractionGameAction gameAction =
            new CharacterInteractionGameAction(CHARACTER_SENDER, CHARACTER_RECEIVER, CHARACTER_TO_CHARACTER_ACTION_TYPE, A_TURN_NUMBER);

        characterInteractionActionListener.onGameAction(gameAction);

        Mockito.verify(characterInteractionService)
               .executeCharacterInteraction(CHARACTER_SENDER, CHARACTER_RECEIVER, CHARACTER_TO_CHARACTER_ACTION_TYPE, A_TURN_NUMBER);
    }

    @Test
    public void givenAnyOtherGameAction_whenOnGameAction_thenDoNothing() {
        GameAction notSupportedGameAction = new ResetGameGameAction();

        characterInteractionActionListener.onGameAction(notSupportedGameAction);

        Mockito.verifyNoInteractions(characterInteractionService);
    }
}
