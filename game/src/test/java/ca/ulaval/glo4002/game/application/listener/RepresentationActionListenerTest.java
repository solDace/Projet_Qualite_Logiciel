package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.RepresentationService;
import ca.ulaval.glo4002.game.domain.action.AllAgentsRepresentationRequestGameAction;
import ca.ulaval.glo4002.game.domain.action.AllCheckAndEliminateGameAction;
import ca.ulaval.glo4002.game.domain.action.AllPayAgentGameAction;
import ca.ulaval.glo4002.game.domain.action.AllPayLawyerGameAction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RepresentationActionListenerTest {

    @Mock
    private RepresentationService representationService;
    private RepresentationActionListener representationActionListener;

    @BeforeEach
    public void setUp() {
        representationActionListener = new RepresentationActionListener(representationService);
    }

    @Test
    public void givenAllAgentsRepresentationRequestAction_whenOnGameAction_thenMakeAllRepresentationArrangements() {
        AllAgentsRepresentationRequestGameAction gameAction = new AllAgentsRepresentationRequestGameAction();

        representationActionListener.onGameAction(gameAction);

        Mockito.verify(representationService).makeAllRepresentationArrangements();
    }

    @Test
    public void givenAllPayAgentGameAction_whenOnGameAction_thenPayAllRepresentingAgents() {
        AllPayAgentGameAction gameAction = new AllPayAgentGameAction();

        representationActionListener.onGameAction(gameAction);

        Mockito.verify(representationService).payAllRepresentingAgents();
    }

    @Test
    public void givenAllPayLawyerGameAction_whenOnGameAction_thenPayAllRepresentingLawyers() {
        AllPayLawyerGameAction gameAction = new AllPayLawyerGameAction();

        representationActionListener.onGameAction(gameAction);

        Mockito.verify(representationService).payAllRepresentingLawyers();
    }

    @Test
    public void givenAnyOtherGameAction_whenOnGameAction_thenDoNothing() {
        AllCheckAndEliminateGameAction notSupportedGameAction = new AllCheckAndEliminateGameAction();

        representationActionListener.onGameAction(notSupportedGameAction);

        Mockito.verifyNoInteractions(representationService);
    }
}
