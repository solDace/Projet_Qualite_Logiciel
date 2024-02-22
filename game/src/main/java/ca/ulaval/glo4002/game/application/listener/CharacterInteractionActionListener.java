package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.CharacterInteractionService;
import ca.ulaval.glo4002.game.application.GameActionListener;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionGameAction;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.action.GameActionType;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class CharacterInteractionActionListener implements GameActionListener {

    private final CharacterInteractionService characterInteractionService;

    @Inject
    public CharacterInteractionActionListener(CharacterInteractionService characterInteractionService) {
        this.characterInteractionService = characterInteractionService;
    }

    @Override
    public void onGameAction(GameAction gameAction) {
        if (gameAction.getActionType().equals(GameActionType.CHARACTER_TO_CHARACTER_ACTION)) {
            CharacterInteractionGameAction convertedGameAction = (CharacterInteractionGameAction) gameAction;

            characterInteractionService.executeCharacterInteraction(
                convertedGameAction.senderID(),
                convertedGameAction.receiverID(),
                convertedGameAction.characterInteractionActionType(),
                convertedGameAction.turnNumber());
        }
    }
}
