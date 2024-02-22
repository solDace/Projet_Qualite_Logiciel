package ca.ulaval.glo4002.game.domain.action;

public record AllAgentsRepresentationRequestGameAction() implements GameAction {
    @Override
    public GameActionType getActionType() {
        return GameActionType.ALL_AGENTS_REPRESENTATION_REQUEST;
    }
}
