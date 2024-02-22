package ca.ulaval.glo4002.game.domain.action;

public record AllPayLawyerGameAction() implements GameAction {
    @Override
    public GameActionType getActionType() {
        return GameActionType.ALL_PAY_LAWYER;
    }
}
