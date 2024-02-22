package ca.ulaval.glo4002.game.domain.action;

public record AllSettleLawsuits() implements GameAction  {
    @Override
    public GameActionType getActionType() {
        return GameActionType.ALL_SETTLE_LAWSUITS;
    }
}
