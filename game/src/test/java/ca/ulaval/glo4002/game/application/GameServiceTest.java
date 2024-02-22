package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionGameAction;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionGameActionFactory;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.action.ResetGameGameAction;
import ca.ulaval.glo4002.game.domain.game.Game;
import ca.ulaval.glo4002.game.domain.game.GameRepository;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    private static final int EXISTING_GAME_TURN = 5;
    private static final String SENDING_CHARACTER = "Bobino";
    private static final String RECEIVING_CHARACTER = "Bobinette";
    private static final CharacterInteractionActionType ACTION_TYPE = CharacterInteractionActionType.GOSSIP;

    @Mock
    private GameAction gameAction;
    @Mock
    private Game existingGame;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private GameActionEmitter gameActionEmitter;
    @Mock
    private CharacterInteractionGameActionFactory actionFactory;

    private GameService gameService;

    @BeforeEach
    public void setUp() {
        gameService = new GameService(gameRepository, gameActionEmitter, actionFactory);
    }

    @Test
    public void givenAGame_whenExecuteTurn_thenReturnCurrentTurn() {
        givenAGameWithATurn();

        int returnedTurn = gameService.executeTurn();

        assertEquals(EXISTING_GAME_TURN, returnedTurn);
    }

    @Test
    public void givenAGame_whenExecuteTurn_thenReturnExistingGameCurrentTurn() {
        givenAGameWithATurn();

        gameService.executeTurn();

        Mockito.verify(existingGame).nextTurn();
    }

    @Test
    public void givenAGame_whenExecuteTurn_thenSaveExistingGame() {
        givenAGameWithATurn();

        gameService.executeTurn();

        Mockito.verify(gameRepository).saveCurrentGame(existingGame);
    }

    @Test
    public void givenAGameWithAccumulatedActions_whenExecuteTurn_thenSendAllActionsToTheEmitter() {
        Game game = givenAGameWithATurn();
        List<GameAction> actionList = new LinkedList<>();
        Mockito.when(game.nextTurn()).thenReturn(actionList);

        gameService.executeTurn();

        Mockito.verify(gameActionEmitter).executeAllActions(actionList);
    }

    @Test
    public void givenAGame_whenAddAction_thenAddActionToExistingGame() {
        givenAGame();

        gameService.addAction(gameAction);

        Mockito.verify(existingGame).addAction(gameAction);
    }

    @Test
    public void givenAGame_whenAddAction_thenSaveExistingGame() {
        givenAGame();

        gameService.addAction(gameAction);

        Mockito.verify(gameRepository).saveCurrentGame(existingGame);
    }

    @Test
    public void givenAGameTurnASendingAndReceivingCharacterAndAnActionType_whenAddCharacterAction_thenCreateAnActionWithThoseParameters() {
        givenAGameWithATurn();

        gameService.addCharacterInteraction(SENDING_CHARACTER, RECEIVING_CHARACTER, ACTION_TYPE);

        Mockito.verify(actionFactory)
               .createCharacterInteractionGameAction(SENDING_CHARACTER, RECEIVING_CHARACTER, ACTION_TYPE, EXISTING_GAME_TURN + 1);
    }

    @Test
    public void givenAGameTurnASendingAndReceivingCharacterAndAnActionType_whenAddCharacterAction_thenAddAnActionToTheGame() {
        CharacterInteractionGameAction createdAction = Mockito.mock(CharacterInteractionGameAction.class);
        givenAGameWithATurn();
        Mockito.when(actionFactory.createCharacterInteractionGameAction(SENDING_CHARACTER, RECEIVING_CHARACTER, ACTION_TYPE, EXISTING_GAME_TURN + 1))
               .thenReturn(createdAction);

        gameService.addCharacterInteraction(SENDING_CHARACTER, RECEIVING_CHARACTER, ACTION_TYPE);

        Mockito.verify(existingGame).addCharacterInteraction(createdAction);
    }

    @Test
    public void givenAGame_whenAddCharacterAction_thenSaveExistingGame() {
        givenAGame();

        gameService.addCharacterInteraction(SENDING_CHARACTER, RECEIVING_CHARACTER, ACTION_TYPE);

        Mockito.verify(gameRepository).saveCurrentGame(existingGame);
    }

    @Test
    public void givenAGame_whenResetGame_thenResetExistingGame() {
        givenAGame();

        gameService.resetGame();

        Mockito.verify(existingGame).reset();
    }

    @Test
    public void givenAGame_whenResetGame_thenSaveExistingGame() {
        givenAGame();

        gameService.resetGame();

        Mockito.verify(gameRepository).saveCurrentGame(existingGame);
    }

    @Test
    public void givenAGame_whenResetGame_thenEmitEliminationAction() {
        givenAGame();

        gameService.resetGame();

        Mockito.verify(gameActionEmitter).emitAction(any(ResetGameGameAction.class));
    }

    private void givenAGame() {
        Mockito.when(gameRepository.getCurrentGame()).thenReturn(existingGame);
    }

    private Game givenAGameWithATurn() {
        Mockito.when(existingGame.getTurnNumber()).thenReturn(EXISTING_GAME_TURN);
        Mockito.when(gameRepository.getCurrentGame()).thenReturn(existingGame);

        return existingGame;
    }
}
