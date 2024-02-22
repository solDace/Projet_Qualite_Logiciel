package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.CharacterService;
import ca.ulaval.glo4002.game.application.GameActionListener;
import ca.ulaval.glo4002.game.domain.action.AddCharacterGameAction;
import ca.ulaval.glo4002.game.domain.action.AllLoseMoneyGameAction;
import ca.ulaval.glo4002.game.domain.action.AllLoseReputationPointsGameAction;
import ca.ulaval.glo4002.game.domain.action.GameAction;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class CharacterActionListener implements GameActionListener {

    private final CharacterService characterService;

    @Inject
    public CharacterActionListener(CharacterService characterService) {
        this.characterService = characterService;
    }

    @Override
    public void onGameAction(GameAction gameAction) {
        switch (gameAction.getActionType()) {
            case ADD_CHARACTER -> {
                AddCharacterGameAction convertedGameAction = (AddCharacterGameAction) gameAction;
                characterService.addCharacter(convertedGameAction.name(), convertedGameAction.salary(), convertedGameAction.characterType());
            }
            case ALL_LOSE_REPUTATION_POINTS -> {
                AllLoseReputationPointsGameAction convertedGameAction = (AllLoseReputationPointsGameAction) gameAction;
                characterService.allLoseReputationPoints(convertedGameAction.nbReputationPoints());
            }
            case ALL_LOSE_MONEY -> {
                AllLoseMoneyGameAction convertedGameAction = (AllLoseMoneyGameAction) gameAction;
                characterService.allLoseMoney(convertedGameAction.moneyAmount());
            }
            case ALL_CHARACTERS_NEXT_TURN -> {
                characterService.allNextTurn();
            }
        }
    }
}
