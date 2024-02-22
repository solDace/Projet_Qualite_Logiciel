package ca.ulaval.glo4002.game.domain.action;

import ca.ulaval.glo4002.game.domain.money.Money;

public record AllLoseMoneyGameAction(Money moneyAmount) implements GameAction {

    @Override
    public GameActionType getActionType() {
        return GameActionType.ALL_LOSE_MONEY;
    }
}
