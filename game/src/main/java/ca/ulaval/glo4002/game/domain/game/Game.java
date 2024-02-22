package ca.ulaval.glo4002.game.domain.game;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionGameAction;
import ca.ulaval.glo4002.game.domain.action.GameAction;

import java.util.List;

public class Game {
    private static final int INITIAL_TURN_NUMBER = 0;
    private Turn currentTurn;

    public Game() {
        createNextTurn(INITIAL_TURN_NUMBER);
    }

    public List<GameAction> nextTurn() {
        Turn oldTurn = currentTurn;

        createNextTurn(oldTurn.getTurnNumber() + 1);

        return oldTurn.generateActionList();
    }

    public int getTurnNumber() {
        return currentTurn.getTurnNumber();
    }

    public void reset() {
        createNextTurn(INITIAL_TURN_NUMBER);
    }

    public void addAction(GameAction gameAction) {
        currentTurn.addAction(gameAction);
    }

    public void addCharacterInteraction(CharacterInteractionGameAction characterAction) {
        currentTurn.addCharacterAction(characterAction);
    }

    private void createNextTurn(int turnNumber) {
        currentTurn = new Turn(turnNumber);
    }
}
