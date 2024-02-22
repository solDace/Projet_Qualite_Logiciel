package ca.ulaval.glo4002.game.domain.game;

import ca.ulaval.glo4002.game.domain.action.AddCharacterGameAction;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionGameAction;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.action.GameActionType;
import ca.ulaval.glo4002.game.domain.character.CharacterType;
import ca.ulaval.glo4002.game.domain.money.Money;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {
    private static final String NAME = "Bob";
    private static final Money SALARY = new Money(12354.0f);
    private static final CharacterType CHARACTER_TYPE = CharacterType.ACTOR;
    private static final int GAME_TURN = 5;
    private static final String SENDING_CHARACTER = "Bobino";
    private static final String RECEIVING_CHARACTER = "Bobinette";
    private static final CharacterInteractionActionType ACTION_TYPE = CharacterInteractionActionType.GOSSIP;

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void givenAGameOfTwoTurns_whenNextTurn_thenItIsTheThirdTurn() {
        game.nextTurn();
        game.nextTurn();

        game.nextTurn();

        int nextTurn = game.getTurnNumber();
        assertEquals(3, nextTurn);
    }

    @Test
    public void givenAGameOfMultipleTurns_whenResetGame_thenItIsTheFirstTurn() {
        game.nextTurn();
        game.nextTurn();
        game.nextTurn();

        game.reset();
        game.nextTurn();

        int nextTurn = game.getTurnNumber();
        assertEquals(1, nextTurn);
    }

    @Test
    public void givenAGameWithNoAddedActions_whenNoAddCharacterGameActionsAdded_thenAddCharacterGameActionNotInGameActions() {
        List<GameAction> actions = game.nextTurn();

        assertEquals(0, actions.stream().filter(action -> action.getActionType() == GameActionType.ADD_CHARACTER).count());
    }

    @Test
    public void givenACharacterInteraction_whenAddingIt_thenTheActionIsAddedToTheTurn() {
        CharacterInteractionGameAction action = new CharacterInteractionGameAction(SENDING_CHARACTER, RECEIVING_CHARACTER, ACTION_TYPE, GAME_TURN);

        game.addCharacterInteraction(action);

        List<GameAction> actions = game.nextTurn();
        assertTrue(actions.contains(action));
    }

    @Test
    public void givenAGameWithAAddCharacterAction_whenNextTurn_thenAddCharacterActionInGameActions() {
        AddCharacterGameAction addCharacterAction = new AddCharacterGameAction(NAME, SALARY, CHARACTER_TYPE);
        game.addAction(addCharacterAction);

        List<GameAction> actions = game.nextTurn();

        assertTrue(actions.contains(addCharacterAction));
    }
}
