package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.ResetGameService;
import ca.ulaval.glo4002.game.domain.action.AllCheckAndEliminateGameAction;
import ca.ulaval.glo4002.game.domain.action.ResetGameGameAction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResetGameActionListenerTest {

    private ResetGameActionListener resetGameActionListener;

    @Mock
    private ResetGameService resetGameService;

    @BeforeEach
    public void setUp() {
        resetGameActionListener = new ResetGameActionListener(resetGameService);
    }

    @Test
    public void givenResetGameAction_whenOnGameAction_thenResetGameServiceResetTheGame() {
        ResetGameGameAction resetGameGameAction = new ResetGameGameAction();

        resetGameActionListener.onGameAction(resetGameGameAction);

        Mockito.verify(resetGameService).resetAllRepositories();
    }

    @Test
    public void givenAnyOtherGameAction_whenOnGameAction_thenDoNotDeleteAnyCharacters() {
        AllCheckAndEliminateGameAction notSupportedGameAction = new AllCheckAndEliminateGameAction();

        resetGameActionListener.onGameAction(notSupportedGameAction);

        Mockito.verify(resetGameService, Mockito.never()).resetAllRepositories();
    }
}
