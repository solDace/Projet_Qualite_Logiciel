package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.action.GameAction;

public interface GameActionListener {
    void onGameAction(GameAction gameAction);
}
