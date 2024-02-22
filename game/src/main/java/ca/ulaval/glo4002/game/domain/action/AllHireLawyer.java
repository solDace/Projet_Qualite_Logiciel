package ca.ulaval.glo4002.game.domain.action;

public record AllHireLawyer() implements GameAction  {
    @Override
    public GameActionType getActionType() {
        return GameActionType.ALL_HIRE_LAWYER;
    }
}
