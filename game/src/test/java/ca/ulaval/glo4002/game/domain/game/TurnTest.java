package ca.ulaval.glo4002.game.domain.game;

import ca.ulaval.glo4002.game.domain.action.*;
import ca.ulaval.glo4002.game.domain.money.Money;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class TurnTest {

    private static final int TURN_NUMBER = 1;
    private static final String SENDER = "Tommy", RECEIVER = "Prisca", ANOTHER_RECEIVER = "Gaby";
    private static final int REPUTATION_POINTS_LOST_BY_ALL = 6, FOLLOWERS_LOST_BY_ALL = 600, MONEY_LOST_BY_ALL = 100;
    private static final List<GameAction> EARLY_START_OF_TURN_ACTIONS = List.of(
        new AllLoseHamstagramFollowersGameAction(FOLLOWERS_LOST_BY_ALL),
        new AllMoviesChangeStepGameAction(),
        new AllPayAgentGameAction(),
        new AllPayLawyerGameAction(),
        new AllLoseMoneyGameAction(new Money(MONEY_LOST_BY_ALL)),
        new AllLoseReputationPointsGameAction(REPUTATION_POINTS_LOST_BY_ALL),
        new AllSettleLawsuits(),
        new AllHireLawyer()
    );
    private static final List<GameAction> ADDITIONAL_START_TURN_ACTIONS = List.of(
        new AllCheckAndEliminateGameAction()
    );
    private static final List<GameAction> END_OF_TURN_ACTIONS = List.of(
        new AllAgentsRepresentationRequestGameAction(),
        new AllCharactersNextTurn()
    );

    @Mock
    private GameAction gameAction;
    @Mock
    private CharacterInteractionGameAction characterAction;

    private Turn turn;

    @BeforeEach
    public void setUp() {
        turn = new Turn(TURN_NUMBER);
    }

    @Test
    public void whenCreatingANewTurn_thenHasOnlyStandardActions() {
        List<GameAction> standardActions = new ArrayList<>(EARLY_START_OF_TURN_ACTIONS);
        standardActions.addAll(ADDITIONAL_START_TURN_ACTIONS);
        standardActions.addAll(END_OF_TURN_ACTIONS);

        assertEquals(standardActions, turn.generateActionList());
    }

    @Test
    public void givenATurn_whenGenerateActionList_thenAllActionsToExecuteAreReturnedInOrder() {
        turn.addAction(gameAction);
        turn.addCharacterAction(characterAction);

        List<GameAction> returnedActions = turn.generateActionList();
        List<GameAction> allActionsInOrder = new ArrayList<>(EARLY_START_OF_TURN_ACTIONS);
        allActionsInOrder.add(characterAction);
        allActionsInOrder.addAll(ADDITIONAL_START_TURN_ACTIONS);
        allActionsInOrder.add(gameAction);
        allActionsInOrder.addAll(END_OF_TURN_ACTIONS);

        assertEquals(allActionsInOrder, returnedActions);
    }

    @Test
    public void givenAGameAction_whenAddingThatAction_thenThatActionIsAddedBetweenAdditionalStartTurnActionsAndEndOfTurnActions() {

        turn.addAction(gameAction);

        List<GameAction> allActions = new ArrayList<>(EARLY_START_OF_TURN_ACTIONS);
        allActions.addAll(ADDITIONAL_START_TURN_ACTIONS);
        allActions.add(gameAction);
        allActions.addAll(END_OF_TURN_ACTIONS);
        assertEquals(allActions, turn.generateActionList());
    }

    @Test
    public void givenACharacterAction_whenAddingThatAction_thenThatActionIsAddedBetweenEarlyStartOfTurnActionsAndAdditionalStartTurnActions() {

        turn.addCharacterAction(characterAction);

        List<GameAction> allActions = new ArrayList<>(EARLY_START_OF_TURN_ACTIONS);
        allActions.add(characterAction);
        allActions.addAll(ADDITIONAL_START_TURN_ACTIONS);
        allActions.addAll(END_OF_TURN_ACTIONS);
        assertEquals(allActions, turn.generateActionList());
    }

    @Test
    public void givenATurnWithACharacterActionThatCanBeAdded_whenAddCharacterAction_thenCharacterActionIsAdded() {
        CharacterInteractionGameAction characterAction =
            new CharacterInteractionGameAction(SENDER, RECEIVER, CharacterInteractionActionType.PROMOTE_MOVIE, TURN_NUMBER);

        turn.addCharacterAction(characterAction);

        assertTrue(turn.generateActionList().contains(characterAction));
    }

    @Test
    public void givenATurnWithACharacterActionThatCanNotBeAdded_whenAddCharacterAction_thenCharacterActionIsNotAdded() {
        givenATurnWithAGameActionNotRespectingRequirementsToBeAdded();
        CharacterInteractionGameAction characterAction =
            new CharacterInteractionGameAction(SENDER, RECEIVER, CharacterInteractionActionType.COMPLAINT_FOR_HARASSMENT, TURN_NUMBER);

        turn.addCharacterAction(characterAction);

        assertFalse(turn.generateActionList().contains(characterAction));
    }

    private void givenATurnWithAGameActionNotRespectingRequirementsToBeAdded() {
        CharacterInteractionGameAction characterAction =
            new CharacterInteractionGameAction(SENDER, ANOTHER_RECEIVER, CharacterInteractionActionType.PARTICIPATE_TO_REALITY_SHOW, TURN_NUMBER);
        turn.addCharacterAction(characterAction);
    }
}
