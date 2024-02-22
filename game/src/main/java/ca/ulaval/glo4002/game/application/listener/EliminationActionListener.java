package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.EliminationService;
import ca.ulaval.glo4002.game.application.GameActionListener;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.action.GameActionType;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class EliminationActionListener implements GameActionListener {

    private final EliminationService eliminationService;

    @Inject
    public EliminationActionListener(EliminationService eliminationService) {
        this.eliminationService = eliminationService;
    }

    @Override
    public void onGameAction(GameAction gameAction) {
        if (gameAction.getActionType().equals(GameActionType.ALL_CHECK_AND_ELIMINATE)) {
            eliminationService.deleteCharactersAndEverythingLinkedToThem();
        }
    }
}
