package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.GameActionListener;
import ca.ulaval.glo4002.game.application.HamstagramService;
import ca.ulaval.glo4002.game.domain.action.AllLoseHamstagramFollowersGameAction;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.action.GameActionType;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class HamstagramActionListener implements GameActionListener {

    private final HamstagramService hamstagramService;

    @Inject
    public HamstagramActionListener(HamstagramService hamstagramService) {
        this.hamstagramService = hamstagramService;
    }

    @Override
    public void onGameAction(GameAction gameAction) {
        if (gameAction.getActionType() == GameActionType.ALL_LOSE_HAMSTAGRAM_FOLLOWERS) {
            AllLoseHamstagramFollowersGameAction convertedGameAction = (AllLoseHamstagramFollowersGameAction) gameAction;
            hamstagramService.allAccountsLoseFollowers(convertedGameAction.nbFollowersToLose());
        }
    }
}
