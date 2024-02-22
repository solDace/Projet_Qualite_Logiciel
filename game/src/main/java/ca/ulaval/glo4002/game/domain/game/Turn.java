package ca.ulaval.glo4002.game.domain.game;

import ca.ulaval.glo4002.game.domain.action.*;
import ca.ulaval.glo4002.game.domain.money.Money;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Turn {

    private static final int FOLLOWERS_LOST_BY_ALL = 600;
    private static final int MONEY_LOST_BY_ALL = 100;
    private static final int REPUTATION_POINTS_LOST_BY_ALL = 6;
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
    private final int turnNumber;
    private final List<GameAction> gameActionList = new LinkedList<>();
    private final List<CharacterInteractionGameAction> characterActionList = new LinkedList<>();

    public Turn(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public List<GameAction> generateActionList() {
        List<GameAction> actionsToExecute = new ArrayList<>(EARLY_START_OF_TURN_ACTIONS);
        actionsToExecute.addAll(characterActionList);
        actionsToExecute.addAll(ADDITIONAL_START_TURN_ACTIONS);
        actionsToExecute.addAll(gameActionList);
        actionsToExecute.addAll(END_OF_TURN_ACTIONS);

        return actionsToExecute;
    }

    public void addAction(GameAction gameAction) {
        gameActionList.add(gameAction);
    }

    public void addCharacterAction(CharacterInteractionGameAction characterAction) {
        if (canCharacterActionBeAdded(characterAction)) {
            characterActionList.add(characterAction);
        }
    }

    private boolean canCharacterActionBeAdded(CharacterInteractionGameAction characterAction) {
        return characterActionList.stream().noneMatch(action -> action.senderID().equals(characterAction.senderID()) ||
            !(action.receiverID() == null) && action.receiverID().equals(characterAction.receiverID()));
    }
}
