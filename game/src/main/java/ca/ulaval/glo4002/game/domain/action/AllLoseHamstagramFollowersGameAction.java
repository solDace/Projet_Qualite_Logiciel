package ca.ulaval.glo4002.game.domain.action;

public record AllLoseHamstagramFollowersGameAction(int nbFollowersToLose) implements GameAction {

    @Override
    public GameActionType getActionType() {
        return GameActionType.ALL_LOSE_HAMSTAGRAM_FOLLOWERS;
    }
}
