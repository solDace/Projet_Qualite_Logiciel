package ca.ulaval.glo4002.game.domain.action;

public record AllLoseReputationPointsGameAction(int nbReputationPoints) implements GameAction {

    @Override
    public GameActionType getActionType() {
        return GameActionType.ALL_LOSE_REPUTATION_POINTS;
    }
}
