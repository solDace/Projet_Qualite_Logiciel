package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.GameActionListener;
import ca.ulaval.glo4002.game.application.ResetGameService;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.action.GameActionType;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class ResetGameActionListener implements GameActionListener {

    private final ResetGameService resetGameService;

    @Inject
    public ResetGameActionListener(ResetGameService resetGameService) {
        this.resetGameService = resetGameService;
    }

    @Override
    public void onGameAction(GameAction gameAction) {
        if (gameAction.getActionType() == GameActionType.RESET_GAME) {
            resetGameService.resetAllRepositories();
        }
    }
}
