package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.EliminationService;
import ca.ulaval.glo4002.game.domain.action.AllCheckAndEliminateGameAction;
import ca.ulaval.glo4002.game.domain.action.AllLoseHamstagramFollowersGameAction;
import ca.ulaval.glo4002.game.domain.action.GameAction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EliminationActionListenerTest {

    private static final int NB_FOLLOWERS_TO_LOSE = 100;
    @Mock
    private EliminationService eliminationService;

    private EliminationActionListener eliminationActionListener;

    @BeforeEach
    public void setUp() {
        eliminationActionListener = new EliminationActionListener(eliminationService);
    }

    @Test
    public void givenCharactersAndHamstagramAccountsWhichShouldBeDeleted_whenOnGameAction_thenDeleteThem() {
        AllCheckAndEliminateGameAction gameAction = new AllCheckAndEliminateGameAction();

        eliminationActionListener.onGameAction(gameAction);

        Mockito.verify(eliminationService).deleteCharactersAndEverythingLinkedToThem();
    }

    @Test
    public void givenAnyOtherGameAction_whenOnGameAction_thenDoNothing() {
        GameAction notSupportedGameAction = new AllLoseHamstagramFollowersGameAction(NB_FOLLOWERS_TO_LOSE);

        eliminationActionListener.onGameAction(notSupportedGameAction);

        Mockito.verifyNoInteractions(eliminationService);
    }
}
