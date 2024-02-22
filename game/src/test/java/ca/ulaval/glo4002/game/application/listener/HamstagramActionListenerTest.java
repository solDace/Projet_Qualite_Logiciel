package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.HamstagramService;
import ca.ulaval.glo4002.game.domain.action.AllCheckAndEliminateGameAction;
import ca.ulaval.glo4002.game.domain.action.AllLoseHamstagramFollowersGameAction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HamstagramActionListenerTest {

    private static final int NB_FOLLOWER_LOST = 25;
    @Mock
    private HamstagramService hamstagramService;

    private HamstagramActionListener hamstagramActionListener;

    @BeforeEach
    public void setUp() {
        hamstagramActionListener = new HamstagramActionListener(hamstagramService);
    }

    @Test
    public void givenAllLoseReputationAction_whenOnGameAction_thenServiceMakesAllLoseReputation() {
        AllLoseHamstagramFollowersGameAction gameAction = new AllLoseHamstagramFollowersGameAction(NB_FOLLOWER_LOST);

        hamstagramActionListener.onGameAction(gameAction);

        Mockito.verify(hamstagramService).allAccountsLoseFollowers(NB_FOLLOWER_LOST);
    }

    @Test
    public void givenAnyOtherGameAction_whenOnGameAction_thenDoNothing() {
        AllCheckAndEliminateGameAction notSupportedGameAction = new AllCheckAndEliminateGameAction();

        hamstagramActionListener.onGameAction(notSupportedGameAction);

        Mockito.verifyNoInteractions(hamstagramService);
    }
}
