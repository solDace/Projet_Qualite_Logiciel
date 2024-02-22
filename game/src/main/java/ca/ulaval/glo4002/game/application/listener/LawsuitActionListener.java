package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.GameActionListener;
import ca.ulaval.glo4002.game.application.LawsuitService;
import ca.ulaval.glo4002.game.domain.action.GameAction;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class LawsuitActionListener implements GameActionListener {

    private final LawsuitService lawsuitService;

    @Inject
    public LawsuitActionListener(LawsuitService lawsuitService) {
        this.lawsuitService = lawsuitService;
    }

    @Override
    public void onGameAction(GameAction gameAction) {
        switch (gameAction.getActionType()) {
            case ALL_HIRE_LAWYER -> {
                lawsuitService.hireLawyers();
            }
            case ALL_SETTLE_LAWSUITS -> {
                lawsuitService.settleLawsuits();
            }
        }
    }
}
