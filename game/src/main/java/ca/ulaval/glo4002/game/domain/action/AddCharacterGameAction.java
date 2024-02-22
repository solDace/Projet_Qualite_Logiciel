package ca.ulaval.glo4002.game.domain.action;

import ca.ulaval.glo4002.game.domain.character.CharacterType;
import ca.ulaval.glo4002.game.domain.money.Money;

public record AddCharacterGameAction(String name, Money salary, CharacterType characterType) implements GameAction {

    @Override
    public GameActionType getActionType() {
        return GameActionType.ADD_CHARACTER;
    }
}
