package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.GameActionListener;
import ca.ulaval.glo4002.game.application.RattedInService;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.action.GameActionType;
import ca.ulaval.glo4002.game.domain.action.RattedInContactRequestGameAction;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class RattedInActionListener implements GameActionListener {
    private final RattedInService rattedInService;

    @Inject
    public RattedInActionListener(RattedInService rattedInService) {
        this.rattedInService = rattedInService;
    }

    @Override
    public void onGameAction(GameAction gameAction) {
        if (gameAction.getActionType() == GameActionType.RATTEDIN_CONTACT_REQUEST) {
            RattedInContactRequestGameAction convertedGameAction = (RattedInContactRequestGameAction) gameAction;
            rattedInService.makeContactRequest(convertedGameAction.receiverUsername(), convertedGameAction.requesterUsername());
        }
    }
}
