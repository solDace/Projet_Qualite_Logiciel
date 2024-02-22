package ca.ulaval.glo4002.game.domain.action;

public record AllCharactersNextTurn() implements GameAction {
    @Override
    public GameActionType getActionType() {
        return GameActionType.ALL_CHARACTERS_NEXT_TURN;
    }
}
