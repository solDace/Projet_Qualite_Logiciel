package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.RattedInService;
import ca.ulaval.glo4002.game.domain.action.AllCheckAndEliminateGameAction;
import ca.ulaval.glo4002.game.domain.action.RattedInContactRequestGameAction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RattedInActionListenerTest {

    @Mock
    private RattedInService rattedInService;
    private RattedInActionListener rattedinActionListener;

    @BeforeEach
    public void setUp() {
        rattedinActionListener = new RattedInActionListener(rattedInService);
    }

    @Test
    public void givenRequestContactGameAction_whenOnGameAction_thenRattedInServiceMakeContactRequest() {
        String receiverUsername = "Bob";
        String requesterUsername = "Zoe";
        RattedInContactRequestGameAction gameAction = new RattedInContactRequestGameAction(receiverUsername, requesterUsername);

        rattedinActionListener.onGameAction(gameAction);

        Mockito.verify(rattedInService).makeContactRequest(receiverUsername, requesterUsername);
    }

    @Test
    public void givenNotSupportedGameAction_whenOnGameAction_thenDoNotInteractWithRattedInService() {
        AllCheckAndEliminateGameAction notSupportedGameAction = new AllCheckAndEliminateGameAction();

        rattedinActionListener.onGameAction(notSupportedGameAction);

        Mockito.verifyNoInteractions(rattedInService);
    }
}
