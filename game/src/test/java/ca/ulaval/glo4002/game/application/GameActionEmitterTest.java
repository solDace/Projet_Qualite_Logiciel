package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.application.listener.CharacterActionListener;
import ca.ulaval.glo4002.game.application.listener.CharacterInteractionActionListener;
import ca.ulaval.glo4002.game.application.listener.EliminationActionListener;
import ca.ulaval.glo4002.game.application.listener.HamstagramActionListener;
import ca.ulaval.glo4002.game.application.listener.LawsuitActionListener;
import ca.ulaval.glo4002.game.application.listener.MovieActionListener;
import ca.ulaval.glo4002.game.application.listener.RattedInActionListener;
import ca.ulaval.glo4002.game.application.listener.RepresentationActionListener;
import ca.ulaval.glo4002.game.application.listener.ResetGameActionListener;
import ca.ulaval.glo4002.game.domain.action.GameAction;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameActionEmitterTest {

    @Mock
    private CharacterActionListener characterActionListener;
    @Mock
    private HamstagramActionListener hamstagramActionListener;
    @Mock
    private RattedInActionListener rattedInActionListener;
    @Mock
    private EliminationActionListener eliminationActionListener;
    @Mock
    private ResetGameActionListener resetGameActionListener;
    @Mock
    private RepresentationActionListener representationActionListener;

    @Mock
    private GameAction gameAction;
    @Mock
    private GameAction anotherGameAction;
    @Mock
    private MovieActionListener movieActionListener;
    @Mock
    private CharacterInteractionActionListener characterInteractionActionListener;
    @Mock
    private LawsuitActionListener lawsuitActionListener;

    private GameActionEmitter gameActionEmitter;

    @BeforeEach
    public void setUp() {
        gameActionEmitter = new GameActionEmitter(characterActionListener,
                                                  hamstagramActionListener,
                                                  rattedInActionListener,
                                                  eliminationActionListener,
                                                  resetGameActionListener,
                                                  representationActionListener,
                                                  movieActionListener,
                                                  characterInteractionActionListener,
                                                  lawsuitActionListener);
    }

    @Test
    public void givenAnAction_whenEmitAction_thenCharacterActionListenerReceivesTheAction() {

        gameActionEmitter.emitAction(gameAction);

        Mockito.verify(characterActionListener).onGameAction(gameAction);
    }

    @Test
    public void givenAnAction_whenEmitAction_thenHamstagramActionListenerReceivesTheAction() {

        gameActionEmitter.emitAction(gameAction);

        Mockito.verify(hamstagramActionListener).onGameAction(gameAction);
    }

    @Test
    public void givenAnAction_whenEmitAction_thenEliminationActionListenerReceivesTheAction() {

        gameActionEmitter.emitAction(gameAction);

        Mockito.verify(eliminationActionListener).onGameAction(gameAction);
    }

    @Test
    public void givenAnAction_whenEmitAction_thenResetGameActionListenerReceivesTheAction() {

        gameActionEmitter.emitAction(gameAction);

        Mockito.verify(resetGameActionListener).onGameAction(gameAction);
    }

    @Test
    public void givenAnAction_whenEmitAction_thenRattedInActionListenerReceivesTheAction() {

        gameActionEmitter.emitAction(gameAction);

        Mockito.verify(rattedInActionListener).onGameAction(gameAction);
    }

    @Test
    public void givenAnAction_whenEmitAction_thenAgentRepresentationActionListenerReceivesTheAction() {

        gameActionEmitter.emitAction(gameAction);

        Mockito.verify(representationActionListener).onGameAction(gameAction);
    }

    @Test
    public void givenAnAction_whenEmitAction_thenMovieActionListenerReceivesTheAction() {

        gameActionEmitter.emitAction(gameAction);

        Mockito.verify(movieActionListener).onGameAction(gameAction);
    }

    @Test
    public void givenAnAction_whenEmitAction_thenCharacterInteractionActionListenerReceivesTheAction() {

        gameActionEmitter.emitAction(gameAction);

        Mockito.verify(characterInteractionActionListener).onGameAction(gameAction);
    }

    @Test
    public void givenAnAction_whenEmitAction_thenLawsuitActionListenerReceivesTheAction() {

        gameActionEmitter.emitAction(gameAction);

        Mockito.verify(lawsuitActionListener).onGameAction(gameAction);
    }

    @Test
    public void givenListOfTwoActions_whenExecuteAll_thenAllActionAreExecutedInOrder() {
        List<GameAction> listOfActions = new LinkedList<>();
        listOfActions.add(gameAction);
        listOfActions.add(anotherGameAction);

        gameActionEmitter.executeAllActions(listOfActions);

        InOrder inOrderCharacter = Mockito.inOrder(characterActionListener);
        InOrder inOrderElimination = Mockito.inOrder(eliminationActionListener);
        InOrder inOrderHamstagram = Mockito.inOrder(hamstagramActionListener);

        inOrderCharacter.verify(characterActionListener).onGameAction(gameAction);
        inOrderCharacter.verify(characterActionListener).onGameAction(anotherGameAction);
        inOrderElimination.verify(eliminationActionListener).onGameAction(gameAction);
        inOrderElimination.verify(eliminationActionListener).onGameAction(anotherGameAction);
        inOrderHamstagram.verify(hamstagramActionListener).onGameAction(gameAction);
        inOrderHamstagram.verify(hamstagramActionListener).onGameAction(anotherGameAction);
    }
}
